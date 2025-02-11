#!/usr/bin/env python3
import os
import subprocess
import glob
import re

def main():
    """
    Dynamically determine paths based on this script's location.
    We assume:
      1) run_tests.py is in:             <parent_dir>/tolang/run_tests.py
      2) The build output (classes) is in: <parent_dir>/tolang/build/classpath
      3) The test source files are in:     <parent_dir>/tolang/test/java/lang/Test*.java
      4) The 'tolang' binary is in:        <parent_dir>/gtos/build/bin/tolang
    Adjust if your structure differs.
    """

    # 1) script_dir: the directory where run_tests.py resides
    script_dir = os.path.dirname(os.path.realpath(__file__))

    # 2) parent_dir: one level above script_dir => e.g. /Users/tomisetsu/tos-network
    parent_dir = os.path.dirname(script_dir)

    # 3) Path to the build_classpath => .../tolang/build/classpath
    build_classpath = os.path.join(parent_dir, "tolang", "build", "classpath")

    # 4) Find all test source files: .../tolang/test/java/lang/Test*.java
    test_dir = os.path.join(parent_dir, "tolang", "test", "java", "lang")
    test_glob = os.path.join(test_dir, "Test*.java")
    test_sources = glob.glob(test_glob)
    if not test_sources:
        print(f"No test files found for pattern: {test_glob}")
        return

    print("Found test files:")
    for src in test_sources:
        print("  ", src)
    print()

    # 5) Compile all test files together
    print("Compiling all Test*.java files ...")
    compile_cp = f"{build_classpath}:."

    compile_cmd = [
        "javac",
        "-cp", compile_cp,
        "-d", build_classpath
    ] + test_sources

    try:
        subprocess.run(compile_cmd, check=True)
        print("Compilation successful.\n")
    except subprocess.CalledProcessError:
        print("Compilation failed.")
        return

    # 6) For each test source, deduce the class name => "java.lang.TestXYZ"
    #    Then run it via your tolang binary.

    tolang_bin = os.path.join(parent_dir, "gtos", "build", "bin", "tolang")
    run_cp = f"{build_classpath}:."
    test_class_names = []

    for source in test_sources:
        filename = os.path.basename(source)   # "TestXYZ.java"
        classname = os.path.splitext(filename)[0]  # "TestXYZ"
        # We assume they are in package "java.lang"
        fqcn = f"java.lang.{classname}"
        test_class_names.append(fqcn)

    # 7) Run each test class
    for fqcn in test_class_names:
        print(f"Running {fqcn}...\n")
        run_cmd = [
            tolang_bin,
            "-classpath", run_cp,
            "-XuseJavaHome",
            fqcn
        ]
        result = subprocess.run(run_cmd, capture_output=True, text=True)

        # Print output from the test run
        print(result.stdout, end="")
        print(result.stderr, end="")

        if result.returncode != 0:
            print(f"{fqcn} failed or encountered an error (exit code {result.returncode}).\n")
        else:
            print(f"{fqcn} passed (exit code 0).\n")


if __name__ == "__main__":
    main()