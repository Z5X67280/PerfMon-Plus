export BOT_API_KEY="963254339:AAGF81vp_kdZ-eXEV_xoOuJDgytQpont9y4"
export TELEGRAM_ID="-1001268516549"

mv app/build/outputs/apk/release/app-release-unsigned.apk app/build/outputs/apk/release/PerfMon-Plus-Translated-UNSIGNED.apk

function push_package() {
	JIP="app/build/outputs/apk/release/PerfMon-Plus-Translated-UNSIGNED.apk"
	curl -F document=@$JIP  "https://api.telegram.org/bot$BOT_API_KEY/sendDocument" \
	     -F chat_id="$TELEGRAM_ID"
}

push_package
