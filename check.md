New-Item -Path "$env:LOCALAPPDATA\persistence_test.txt" -Value "test" -Force
echo "Created test file. Log out and back in, then check if this file exists:"
echo "$env:LOCALAPPDATA\persistence_test.txt"



net use