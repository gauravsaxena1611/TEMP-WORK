"hello" | Out-File "$env:USERPROFILE\Desktop\test123.txt"


Get-WmiObject Win32_DiskDrive | Select-Object Model, InterfaceType, MediaType


Get-WmiObject Win32_DiskDrive | Select-Object Model, InterfaceType, MediaType



Test-Path "$env:LOCALAPPDATA\persistence_test.txt"