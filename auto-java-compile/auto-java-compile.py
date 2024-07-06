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

def show_menu(src_folder) -> str:
	"""
	Shows a menu with the available options to the user and the path of the informed source folder.

	Args:
		src_folder (str): The absolute path of the source folder.
	
	Returns:
		str: The user's choice.
	"""
	print(f"{terminal_colors.HEADER} - Automated Java Compilation Script by Equiel_1703 - {terminal_colors.ENDC}")
	print()
	print(f"{terminal_colors.OKBLUE}Source Folder (absolute path): {src_folder}{terminal_colors.ENDC}")
	print()
	print("Choose an option:")
	print("1. Compile all Java files in the source folder")
	print("2. Compile specific Java files in the source folder")
	print("3. Clear build")
	print("4. Exit")

	choice = input().strip()
	return choice

def posorder_traversal(folder, condition) -> list[str]:
	"""
	Recursively traverses a folder and its subfolders in postorder,
	returning a list of absolute paths of files that satisfy the condition function.

	Args:
		folder (str): The absolute path of the folder to traverse.
		condition (function): A function that receives a file path and returns a boolean value.

	Returns:
		list[str]: A list of absolute paths of the files that satisfy the condition function
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

def compile_file(path_to_file: str, class_path: str) -> None:
	"""
	Compiles a Java file.

	Args:
		path_to_file (str): The absolute path of the file to compile.
		class_path (str): The class path to use when compiling the file.
	"""
	# Command to compile the files
	command = ["javac", "-cp", class_path]

	# Compile the file
	result = subprocess.run(command + [path_to_file], capture_output=True, text=True)

	if result.returncode != 0:
		print(f"{terminal_colors.FAIL}Error> Could not compile the file \"{path_to_file}\"!", file=sys.stderr)
		print(f"Compilation aborted.{terminal_colors.ENDC}", file=sys.stderr)
		print("\n" + result.stderr, file=sys.stderr)

		exit(1)

def compile_all_files(src_folder) -> None:
	"""
	Compiles all Java files in the source folder.

	Args:
		src_folder (str): The absolute path of the source folder.
	"""
	# Get the list of files that will be compiled
	files_to_compile = posorder_traversal(src_folder, lambda file: file.endswith(".java"))

	# Print the list of files
	print("Files to compile:")
	for file in files_to_compile:
		print("+ " + file)

	# Now we can compile the files in the list
	for file in files_to_compile:
		print(f"\n{terminal_colors.OKCYAN}Compiling file: {file}{terminal_colors.ENDC}")
		compile_file(file, src_folder)

	print(f"\n{terminal_colors.OKGREEN}Compilation finished!{terminal_colors.ENDC}")
	print(f"Total of {len(files_to_compile)} files compiled.")
	print(f"{terminal_colors.WARNING}Note: the -cp provided to the Java compiler was the Source Folder path.{terminal_colors.ENDC}")

def compile_specific_files(src_folder) -> None:
	"""
	Compiles specific Java files in the source folder.

	Args:
		src_folder (str): The absolute path of the source folder.
	"""
	files_available = posorder_traversal(src_folder, lambda file: file.endswith(".java"))

	# Print the list of files to the user
	print("Files available to compile:")
	for i, file in enumerate(files_available):
		print(f"{i + 1}. {file.removeprefix(src_folder + os.sep)}")
	
	# Ask the user which files they want to compile
	files_to_compile = input("\nChoose the files to compile (separated by commas): ").split(",")
	files_to_compile = map(str.strip, files_to_compile)
	files_to_compile = [*filter(lambda file_index: file_index.isdigit() and 1 <= int(file_index) <= len(files_available), files_to_compile)]

	if len(files_to_compile) == 0:
		print(f"{terminal_colors.FAIL}Error> No files were selected!{terminal_colors.ENDC}", file=sys.stderr)
		return
	
	# Get the files paths
	files_to_compile = list(map(lambda file_index: files_available[int(file_index) - 1], files_to_compile))

	for file in files_to_compile:
		print(f"\n{terminal_colors.OKCYAN}Compiling file: {file}{terminal_colors.ENDC}")
		compile_file(file, src_folder)
	
	print(f"\n{terminal_colors.OKGREEN}Compilation finished!{terminal_colors.ENDC}")
	print(f"Total of {len(files_to_compile)} files compiled.")
	print(f"{terminal_colors.WARNING}Note: the -cp provided to the Java compiler was the Source Folder path.{terminal_colors.ENDC}")

def clear_build(src_folder) -> None:
	"""
	Removes all .class files from the source folder.

	Args:
		src_folder (str): The absolute path of the source folder.
	"""
	files_to_delete = posorder_traversal(src_folder, lambda file: file.endswith(".class"))

	# Print to the user
	print(f"{terminal_colors.OKCYAN}Cleaning build...{terminal_colors.ENDC}")
	print()

	for file in files_to_delete:
		os.remove(file)
		print(f"{terminal_colors.WARNING}Deleted: {file}{terminal_colors.ENDC}")
	
	print(f"\n{terminal_colors.OKGREEN}Build cleaned!{terminal_colors.ENDC}")

if os.name == "nt":
	# If the OS is Windows, we must enable colors in the terminal
	os.system("color")
	# Also, we must use the "cls" command to clear the terminal
	clear_command = "cls"
else:
	# If the OS is not Windows, we must use the "clear" command
	clear_command = "clear"

# Check if the user provided too many arguments
if len(sys.argv) > 2:
	print(f"{terminal_colors.FAIL}Error> Too many arguments provided!{terminal_colors.ENDC}", file=sys.stderr)
	print(f"Usage: python {sys.argv[0]} [source_folder]", file=sys.stderr)
	exit(1)

# If no arguments were provided, we must ask the user for the source folder
if len(sys.argv) == 1:
	src_folder = input("Folder containing the source code: ").strip()
else:
	# If the user provided a single argument, we must use it as the source folder
	src_folder = sys.argv[1]

# Get the absolute path of the source folder
src_folder = os.path.abspath(src_folder)

# Check if the source folder exists and is a folder
if not os.path.exists(src_folder) or not os.path.isdir(src_folder):
	print(f"{terminal_colors.FAIL}Error> The folder \"{os.path.basename(src_folder)}\" does not exist based on the current location!{terminal_colors.ENDC}", file=sys.stderr)
	exit(1)

while True:
	choice = show_menu(src_folder)

	if choice == "1":
		# Compile all Java files in the source folder
		os.system(clear_command)
		compile_all_files(src_folder)
	elif choice == "2":
		# Compile specific Java files in the source folder
		os.system(clear_command)
		compile_specific_files(src_folder)
	elif choice == "3":
		# Clear build
		os.system(clear_command)
		clear_build(src_folder)
	elif choice == "4":
		# Exit the program
		print(f"{terminal_colors.OKCYAN}Exiting the program...{terminal_colors.ENDC}")
		exit(0)
	else:
		print(f"{terminal_colors.FAIL}Error> Invalid option!{terminal_colors.ENDC}", file=sys.stderr)
		continue

	input("\nPress Enter to continue...")
	os.system(clear_command)