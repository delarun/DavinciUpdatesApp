#!/bin/bash

set +e
sudo apt-get update > /dev/null
sudo apt-get install -y jq > /dev/null

edit_ci_message() {
	MESSAGE_TEXT="$MESSAGE_TEXT
$1"
	curl -s -X POST https://api.telegram.org/bot${ZadliCI_Token}/editMessageText -d chat_id="$TG_DUP_CHAT_ID" -d message_id="$MESSAGE_ID" -d text="$MESSAGE_TEXT" | jq .
}

LAST_COMMIT=$(git rev-parse --short HEAD)

MESSAGE_TEXT="Davinci Updates App
A new commit has been pushed ($LAST_COMMIT)"

CHANGED_JSONS="$(git diff --name-only HEAD~1 . | grep .json)"

if [ "$CHANGED_JSONS" = "" ]; then
	status_code=$(curl --write-out %{http_code} --silent --output /dev/null -X POST http://${ZadliCI_Server_IP}/dup_build)

	if [[ "$status_code" -ne 200 ]] ; then
		echo "Server status changed to $status_code"
		SEMAPHORE_JOB_RESULT="failed"
		export SEMAPHORE_JOB_RESULT=failed
		exit
	fi

else
	MESSAGE="$(curl -s -X POST https://api.telegram.org/bot${ZadliCI_Token}/sendMessage -d chat_id="$TG_DUP_CHAT_ID" -d text="$MESSAGE_TEXT" | jq .)"
	echo "$MESSAGE"

	MESSAGE_ID="$(echo "$MESSAGE" | jq .result.message_id)"
	echo "Message ID: $MESSAGE_ID"
	edit_ci_message "First step: Checking changed JSONs"
	edit_ci_message " "
	for json in $CHANGED_JSONS; do
		jq . $json
		if [ "$?" = 0 ]; then
			edit_ci_message "JSONs has passed the syntax test"
			edit_ci_message " "
		else
			edit_ci_message "JSONs has failed the syntax test"
			edit_ci_message " "
			SYNTAX_CHECK_RESULT="failed"
		fi
	done
	if [ "$SYNTAX_CHECK_RESULT" != "failed" ]; then
		edit_ci_message "All modified JSONs are correct!"
		edit_ci_message " "
		edit_ci_message "$(git log --format=%B -n 1 HEAD~0)"
	else
		edit_ci_message "Who broken JSONs files?"
		SEMAPHORE_JOB_RESULT="failed"
		export SEMAPHORE_JOB_RESULT=failed
		exit
	fi
fi