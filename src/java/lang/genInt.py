#!/usr/bin/env python3

import math

# List of bit sizes to generate
BIT_SIZES = [
    8, 16, 24, 32, 40, 48, 56, 64,
    72, 80, 88, 96, 104, 112, 120, 128,
    136, 144, 152, 160, 168, 176, 184, 192,
    200, 208, 216, 224, 232, 240, 248, 256
]

TEMPLATE_FILE = "uintXXX.java.template"

def main():
    # Read the template content
    with open(TEMPLATE_FILE, "r", encoding="utf-8") as f:
        template = f.read()

    # For each bit size, generate the Java source
    for bits in BIT_SIZES:
        classname = f"uint{bits}"
        # Compute the number of 32-bit words needed
        maxwidth = (bits + 31) // 32  # integer division rounding up

        # Replace placeholders in the template
        source = template
        source = source.replace("__CLASSNAME__", classname)
        source = source.replace("__BITS__", str(bits))
        source = source.replace("__MAXWIDTH__", str(maxwidth))

        # Write out to a file named e.g. "uint8.java", "uint16.java", etc.
        filename = f"{classname}.java"
        with open(filename, "w", encoding="utf-8") as out:
            out.write(source)

        print(f"Generated {filename}")

if __name__ == "__main__":
    main()