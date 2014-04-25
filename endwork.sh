echo "Adding.."
git add ./*
echo -n "Commit: "
read text
git commit -m "$text"

echo "Pushing..."
git push
