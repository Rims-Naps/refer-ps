package org.necrotic.client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import org.necrotic.Configuration;


public class CacheDownloader implements Runnable {
	private static final String CACHE_PATH = System.getProperty("user.home") + File.separator + Configuration.CACHE_DIRECTORY_NAME + File.separator;

	private static final String ZIP_URL = "http://localhost/.SolakRelease.zip";


	private static final String VERSION_FILE = CACHE_PATH + "cache_version.txt";
	private static final int CACHE_VERSION = 13;

	private CacheDownloader.GUI g;

	@SuppressWarnings("resource")  //
	public long getCurrentVersion() {
		try {
			File versionDir = new File(VERSION_FILE);

			if (!versionDir.exists()) {
				versionDir.createNewFile();
				return -1;
			}

			return Long.parseLong(new BufferedReader(new InputStreamReader(new FileInputStream(VERSION_FILE))).readLine());
		} catch (Exception e) {
			return -1;
		}
	}

	public long getNewestVersion() {
		try {
			return ((HttpURLConnection) new URL(ZIP_URL).openConnection()).getContentLengthLong();
		} catch (Exception e) {
			handleException(e);
			return -1;
		}
	}

	private void handleException(Exception e) {
		StringBuffer strBuff = new StringBuffer();

		strBuff.append("Please Screenshot this message, and send it to an admin!\r\n\r\n");
		strBuff.append(e.getClass().getName() + " \"" + e.getMessage() + "\"\r\n");

		for (StackTraceElement s : e.getStackTrace()) {
			strBuff.append(s.toString() + "\r\n");
		}

		alert("Exception [" + e.getClass().getSimpleName() + "]", strBuff.toString(), true);
	}

	private void alert(String title, String msg, boolean error) {
		JOptionPane.showMessageDialog(null, msg, title, (error ? JOptionPane.ERROR_MESSAGE : JOptionPane.PLAIN_MESSAGE));
	}

	public boolean running;

	@SuppressWarnings("resource")
	@Override
	public void run() {
		try {
			long newest = CACHE_VERSION;
			long current = getCurrentVersion();

			if (newest != current) {
				g = new CacheDownloader.GUI();
				g.setLocationRelativeTo(null);
				g.setVisible(true);
				updateCache();
				running = true;
				new FileOutputStream(VERSION_FILE).write(String.valueOf(newest).getBytes());
				g.dispose();
			}
		} catch (Exception e) {
			running = false;
			handleException(e);
		}
	}


	private void updateCache() {
		File clientZip = downloadCache();

		if (clientZip != null) {
			unZip(clientZip);
		}
	}

	private void unZip(File clientZip) {
		try {
			unZipFile(clientZip, new File(Signlink.getCacheDirectory()));
			Files.delete(clientZip.toPath());
		} catch (Exception e) {
			handleException(e);
		}
	}

	private void unZipFile(File zipFile, File outFile) throws IOException {
		g.setStatus("Extracting Solak Cache");
		g.setPercent(0);

		ZipInputStream zin = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFile)));

		ZipEntry e;

		long max = 0;
		long curr = 0;

		while ((e = zin.getNextEntry()) != null) {
			max += e.getSize();
		}

		zin.close();

		ZipInputStream in = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFile)));

		while ((e = in.getNextEntry()) != null) {
			if (e.isDirectory()) {
				new File(outFile, e.getName()).mkdirs();
			} else {
				FileOutputStream out = new FileOutputStream(new File(outFile, e.getName()));

				byte[] b = new byte[1024];

				int len;

				while ((len = in.read(b, 0, b.length)) > -1) {
					curr += len;
					out.write(b, 0, len);
					g.setPercent((int) ((curr * 100) / max));
				}

				out.flush();
				out.close();
				running = false;
			}
		}

		in.close();
	}

	private File downloadCache() {
		g.setStatus("Downloading...");
		File dir = new File(CACHE_PATH);
		if (!dir.exists())
			dir.mkdirs();
		File ret = new File(CACHE_PATH + (Configuration.BETA ? "SolakBeta.zip" : ".SolakRelease.zip"));

		try (OutputStream out = new FileOutputStream(ret)) {
			URLConnection conn = new URL(ZIP_URL).openConnection();
			InputStream in = conn.getInputStream();

			long max = conn.getContentLength();
			long curr = 0;

			byte[] b = new byte[1024];

			int len;

			while ((len = in.read(b, 0, b.length)) > -1) {
				out.write(b, 0, len);
				curr += len;
				g.setPercent((int) ((curr * 100) / max));
			}

			out.flush();
			in.close();
			return ret;
		} catch (Exception e) {
			handleException(e);
			running = false;
			ret.delete();
			return null;
		}
	}

	public class GUI extends JFrame {
		private static final long serialVersionUID = 1L;

		/** Creates new form GUI */
		public GUI() {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception ignored) {

			}

			initComponents();
		}

		/**
		 * This method is called from within the constructor to initialize the
		 * form. WARNING: Do NOT modify this code. The content of this method is
		 * always regenerated by the Form Editor.
		 */
		private void initComponents() {
			jProgressBar1 = new JProgressBar();
			jLabel1 = new JLabel();
			jLabel2 = new JLabel();
			jLabel3 = new JLabel();

			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			setTitle("The Solak Cache is downloading...");

			addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowClosing(java.awt.event.WindowEvent evt) {
					formWindowClosing(evt);
				}
			});

			jLabel1.setText("Status:");
			jLabel2.setText("N/A");
			jLabel3.setText("0%");

			GroupLayout layout = new GroupLayout(getContentPane());
			getContentPane().setLayout(layout);
			layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
					layout.createSequentialGroup()
							.addContainerGap()
							.addGroup(
									layout.createParallelGroup(GroupLayout.Alignment.LEADING)
											.addGroup(
													layout.createSequentialGroup().addComponent(jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 304, Short.MAX_VALUE)
															.addComponent(jLabel3)).addComponent(jProgressBar1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)).addContainerGap()));
			layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
					layout.createSequentialGroup()
							.addContainerGap()
							.addGroup(
									layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
											.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel3))
											.addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(jProgressBar1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
			pack();
		}

		private void formWindowClosing(java.awt.event.WindowEvent evt) {

		}

		private int percent = 0;

		public void setStatus(String s) {
			jLabel2.setText(s);
		}

		public String getStatus() {
			return jLabel2.getText();
		}

		public void setPercent(int amount) {
			percent = amount;
			jLabel3.setText(amount + "%");
			jProgressBar1.setValue(amount);
		}

		public int getPercent() {
			return percent;
		}

		private JLabel jLabel1;
		private JLabel jLabel2;
		private JLabel jLabel3;
		private JProgressBar jProgressBar1;
	}
}
