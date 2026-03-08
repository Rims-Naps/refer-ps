# Lunite-ps

## Required Cache (Important)

This project requires external cache files to function correctly.

Due to size constraints, the cache is **not included in the repository**. The cache must be downloaded and hosted by you so the client can retrieve it through the built-in cache downloader.

---

## Download the Cache

Download the cache archive here:

https://www.dropbox.com/scl/fi/zczpdzosdrmwkwnzyki32/.SolakRelease.zip?rlkey=k1tw6x66k92rhqgn1kns3n6fo&st=ziotgz9m&dl=0

---

## Hosting the Cache

After downloading the cache archive, you must upload it to a file hosting service of your choice. The client will download the cache from this location at runtime.

Steps:

1. Download `.SolakRelease.zip`
2. Upload the file to your preferred file hosting service  
   (examples: personal web server, Dropbox direct link, Google Drive direct download, CDN, etc.)
3. Copy the direct download URL for the file
4. Update the cache download URL in:

```

.\refer-ps\Azura-Client\src\main\java\org\necrotic\client\CacheDownloader.java

```

Replace the existing URL with your hosted cache file URL.

---

## Local Development Setup (XAMPP - Recommended)

For local development, the cache downloader is currently configured to download from:

```

[http://localhost/.SolakRelease.zip](http://localhost/.SolakRelease.zip)

```

To use this setup:

1. Install and start **XAMPP**
2. Copy `.SolakRelease.zip` into:

```

xampp\htdocs

```

3. Ensure XAMPP Apache is running
4. Run the client

The cache downloader will then retrieve the cache from:

```

[http://localhost/.SolakRelease.zip](http://localhost/.SolakRelease.zip)

```

This is the recommended method for **local development environments**.

---

## Usage

Build the project using Maven:

```

mvn install

```

This command will compile the project and generate the following `.jar` files:

```

azura-client-1.0.jar
azura-runelite-1.0.jar
azura-server-1.0.jar

```

After the build completes, you can run the project using the generated jar files 
Note: the project uses jdk 8 for client and server but uses jdk 11 for runelite.