import os
import subprocess
from pathlib import Path

# === Paths ===
BASE_DIR = Path(__file__).resolve().parent.parent  # From src/ -> project/
SOURCE_ROOT = BASE_DIR / "src"  # Where your source files live
MODULE_PATH = BASE_DIR / "target" / "dependency"  # JAR dependencies path
BASE_PACKAGE = "net.alek.buttonclicker"
OUTPUT_DIR = BASE_DIR / "docs"

def generate_javadoc():
    os.makedirs(OUTPUT_DIR, exist_ok=True)

    cmd = [
        "javadoc",
        "--module-path", str(MODULE_PATH),
        "-sourcepath", str(SOURCE_ROOT),
        "-subpackages", BASE_PACKAGE,
        "-d", str(OUTPUT_DIR)
    ]

    print("📢 Running Javadoc command:")
    print(" ".join(cmd))

    result = subprocess.run(cmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)

    if result.returncode == 0:
        print("✅ Javadoc generated successfully at:", OUTPUT_DIR)
    else:
        print("❌ Javadoc generation failed:")
        print(result.stderr)

if __name__ == "__main__":
    generate_javadoc()