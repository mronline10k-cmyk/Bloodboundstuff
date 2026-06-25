# BloodboundResourcePack Plugin

A Minecraft Paper/Spigot plugin that sends custom resource packs to players on join with an approve/decline prompt. Players who decline get kicked with a custom message.

## Features

- Auto-send resource pack on player join
- Custom approve prompt message (supports Minecraft color codes)
- Kick players who decline or fail to download
- Multiple resource packs support (add unlimited packs)
- Admin commands for reloading and manual sending
- Bedrock player support (via Geyser/Floodgate)

## How to Get the Compiled JAR (No Java Knowledge Needed)

### Step 1: Create a GitHub Repository

1. Go to [github.com](https://github.com) and sign up/log in (it's free)
2. Click the **+** button (top right) → **New repository**
3. Name it: `BloodboundResourcePack`
4. Set it to **Public**
5. Click **Create repository**

### Step 2: Upload the Plugin Files

1. On your new repo page, click **"uploading an existing file"**
2. Drag and drop the **ENTIRE** `BloodboundResourcePack` folder contents
   - Include all files: `pom.xml`, `src/`, `.github/`, `config.yml`, `plugin.yml`
3. Click **Commit changes**

### Step 3: Build Automatically

1. Go to the **Actions** tab at the top of your repo
2. You'll see the workflow running automatically (takes ~2 minutes)
3. Wait for the green checkmark ✅
4. Click on the completed workflow run
5. Scroll down to **Artifacts** and click **BloodboundResourcePack**
6. The `.zip` contains your `BloodboundResourcePack-1.0.0.jar`

### Step 4: Install on Your Server

1. Download the artifact ZIP and extract the `.jar`
2. Upload the `.jar` to your server's `/plugins/` folder (via PebbleHost File Manager or FTP)
3. Restart your server

## Configuration

Edit `config.yml` in `/plugins/BloodboundResourcePack/` after first run:

```yaml
resource-packs:
  minimace:
    enabled: true
    url: "https://www.dropbox.com/scl/fi/.../MiniMace_ResourcePack.zip?...&dl=1"
    hash: "76d6b3d5d961cb4b4a4219950c4b14e6890ddebf"
    prompt: "&4&lBloodbound Server &8| &cAccept to see custom textures!"
    required: true

  # Add more packs by copying the block above:
  # bloodarmor:
  #   enabled: true
  #   url: "https://your-url.com/AnotherPack.zip"
  #   hash: "PUT_SHA1_HERE"
  #   prompt: "&4&lBloodbound &8| &cAccept for more textures!"
  #   required: true
```

## Admin Commands

| Command | Permission | Description |
|---------|-----------|-------------|
| `/bloodboundpack reload` | bloodboundpack.admin | Reload config without restart |
| `/bloodboundpack status` | bloodboundpack.admin | See loaded packs |
| `/bloodboundpack send <player>` | bloodboundpack.admin | Force-send to a player |

## How to Get SHA-1 Hash of Your Resource Pack

### Windows (Command Prompt):
```cmd
certutil -hashfile "C:\Users\YourName\Downloads\YourPack.zip" SHA1
```

### Linux/Mac:
```bash
sha1sum YourPack.zip
```

## Requirements

- **Server:** Paper/Spigot 1.21.1+
- **Java:** 21+ (server-side, not your computer)

## Troubleshooting

### Players get "Download failed"
- Make sure your Dropbox URL ends with `dl=1` (direct download)
- If Dropbox blocks it, try GitHub raw links or another host
- Test the URL in your browser — it should start downloading immediately

### Players don't see the prompt
- Check console for errors
- Make sure `enabled: true` in config
- Verify the URL is publicly accessible (not password-protected)

### Plugin doesn't load
- Make sure you're running Paper/Spigot 1.21.1 or newer
- Check console for "BloodboundResourcePack" messages
- Verify Java 21 is installed on your server (PebbleHost should handle this)

## License

This is original code written for Bloodbound Server. Not affiliated with Origins-Reborn.
