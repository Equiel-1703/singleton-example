import os
import sys
import subprocess

class terminal_colors:
	HEADER = '\033[95m'
	OKBLUE = '\033[94m'
	OKCYAN = '\033[96m'
	OKGREEN = '\033[92m'
	WARNING = '\033[93m'
	FAIL = '\033[91m'
	ENDC = '\033[0m'

def posorder_traversal(folder, condition) -> list[str]:
	"""
	Recursively traverses a folder and its subfolders in postorder,
	returning a list of absolute paths of each file that will be compiled.

	The files that will be compiled are the ones that satisfy the condition function.

	Args:
		folder (str): The absolute path of the folder to traverse.
		condition (function): A function that receives a file path and returns a boolean value.

	Returns:
		list[str]: A list of absolute paths of each file that will be compiled.
	"""
	list_of_files = []
	# Get list of files and folders in the received folder
	files = os.listdir(folder)

	for file in files:
		# Join the folder path with the file/folder
		file = os.path.join(folder, file)

		if os.path.isdir(file):
			# If the file is a folder, recursively traverse it and add the files to the list
			list_of_files.extend(posorder_traversal(file, condition))
		elif condition(file):
			# If "file" is a file, we must add it to the list - only if it satisfies the condition
			list_of_files.append(file)
	
	# Return the list of files
	return list_of_files

if os.name == "nt":
	# If the OS is Windows, we must enable colors in the terminal
	os.system("color")

if len(sys.argv) > 2:
	# The user provided more than one argument
	print(f"{terminal_colors.FAIL}Error> Too many arguments provided!{terminal_colors.ENDC}", file=sys.stderr)
	print(f"Usage: python {sys.argv[0]} [source_folder]", file=sys.stderr)
	exit(1)

if len(sys.argv) == 1:
	src_folder = input("Folder containing the source code: ").strip()
else:
	# If the user provided a folder as an argument, we will use it as the source folder
	src_folder = sys.argv[1]

# Get the absolute path of the source folder
src_folder = os.path.abspath(src_folder)
print(f"{terminal_colors.OKCYAN}Source folder (absolute path): {src_folder}{terminal_colors.ENDC}")

# Check if the folder exists and is a directory
if not os.path.exists(src_folder) or not os.path.isdir(src_folder):
	print(f"{terminal_colors.FAIL}Error> The folder \"{os.path.basename(src_folder)}\" does not exist based on the current location!{terminal_colors.ENDC}", file=sys.stderr)
	exit(1)

# Let's now define the condition function
condition = lambda file: file.endswith(".java")

# Get the list of files that will be compiled
files_to_compile = posorder_traversal(src_folder, condition)

# Print the list of files
print("\nFiles to compile:")
for file in files_to_compile:
	print("+ " + file)

# Command to compile the files
command = ["javac", "-cp", src_folder]

# Now we can compile the files in the list
for file in files_to_compile:
	# Compile the file
	result = subprocess.run(command + [file], capture_output=True, text=True)

	if result.returncode != 0:
		print(f"{terminal_colors.FAIL}Error> Could not compile the file \"{file}\"!", file=sys.stderr)
		print(f"Compilation aborted.{terminal_colors.ENDC}")
		print("\n" + result.stdout)

		exit(1)

print(f"\n{terminal_colors.OKGREEN}Compilation finished!{terminal_colors.ENDC}")
print(f"Total of {len(files_to_compile)} files compiled.")
print(f"{terminal_colors.WARNING}Note: the Current Path provided to the Java compiler was the source folder path.{terminal_colors.ENDC}")