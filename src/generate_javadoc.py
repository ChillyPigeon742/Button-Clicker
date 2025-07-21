import os
import subprocess
from pathlib import Path

BASE_PACKAGE = "net.alek.buttonclicker"
OUTPUT_DIR = "assets/buttonclicker/html/docs"

def generate_javadoc():
    import subprocess
    import os
    os.makedirs(OUTPUT_DIR, exist_ok=True)

    cmd = [
        "javadoc",
        "-d", str(OUTPUT_DIR),
        "-subpackages", BASE_PACKAGE
    ]
    result = subprocess.run(cmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)

if __name__ == "__main__":
    generate_javadoc()