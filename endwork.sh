echo "Adding.."
git add ./*.java | /dev/null
git add ./*/*.java | /dev/null
git add ./*/*/*.java | /dev/null
git add ./*/*/*/*.java | /dev/null
git add ./*/*/*/*/*.java | /dev/null
git status
echo -n "Commit: "
read text
git commit -m "$text"

echo "Pushing..."
git push
