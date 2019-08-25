export BOT_API_KEY="963254339:AAGF81vp_kdZ-eXEV_xoOuJDgytQpont9y4"
export TELEGRAM_ID="-1001268516549"

function push_package() {
	JIP="source.zip"
	curl -F document=@$JIP  "https://api.telegram.org/bot$BOT_API_KEY/sendDocument" \
	     -F chat_id="$TELEGRAM_ID"
}

zip -r9 -9 "source.zip" .

push_package
