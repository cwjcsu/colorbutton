package com.cwjcsu.colorbutton;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.lang.ref.WeakReference;

/**
 * Created by cwjcsu@126.com on 15-12-8.
 */
public class GuiUtil {
	private static String OS = System.getProperty("os.name").toLowerCase();

	private static String ARCH = System.getProperty("os.arch").toLowerCase();
	public static final int TOP_LEFT = 1;
	public static final int TOP_RIGHT = 2;
	public static final int BOTTOM_LEFT = 3;
	public static final int BOTTOM_RIGHT = 4;

	public static boolean isWindows() {
		return (OS.indexOf("win") >= 0);
	}

	public static boolean isMac() {
		return (OS.indexOf("mac") >= 0);
	}

	public static boolean isUnix() {
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
	}

	public static boolean isSolaris() {
		return (OS.indexOf("sunos") >= 0);
	}

	/**
	 * 在特定位置（通常是鼠标位置：mx,my）展示组件，startPos 用于设置默认鼠标位置位于菜单的方位。
	 *
	 * @param mx
	 * @param my
	 * @param startPos
	 */
	public static void showAt(Window thiz, Insets shadowInsets, int mx, int my, int startPos, boolean alwaysOnTop) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(thiz.getGraphicsConfiguration());

		int w = thiz.getWidth();
		int h = thiz.getHeight();
		int x = mx - shadowInsets.left;
		int y = my - shadowInsets.bottom;
		if (startPos == TOP_LEFT) {

		} else if (startPos == TOP_RIGHT) {
			x = mx - (w - shadowInsets.right);
		} else if (startPos == BOTTOM_LEFT) {
			y = my - (h - shadowInsets.bottom);
		} else if (startPos == BOTTOM_RIGHT) {
			x = mx - (w - shadowInsets.right);
			y = my - (h - shadowInsets.bottom);
		}

		int maxX = screenSize.width - screenInsets.right - w;
		int maxY = screenSize.height - screenInsets.bottom - h;
		int minY = shadowInsets.top;
		if (x > maxX) {
			x = mx - (w - shadowInsets.left);
		}
		if (y > maxY) {
			y = my - (h - shadowInsets.top);
		}
		if (y < minY) {
			y = my - shadowInsets.top;
		}
		thiz.setAlwaysOnTop(alwaysOnTop);
		thiz.setLocation(x, y);
		thiz.setVisible(true);
	}

	public static void showAt(Window thiz, int x, int y, boolean alwaysOnTop) {
		thiz.setAlwaysOnTop(alwaysOnTop);
		thiz.setLocation(x, y);
		thiz.setVisible(true);
	}

	private static String defaultFontName = "Sans-serif";

	/**
	 * 获取组件所在屏幕的ScreenInsets，从而可以获取到任务栏的高度
	 *
	 * @param component
	 * @return
	 */
	public static Insets getScreenInsets(Component component) {
		return Toolkit.getDefaultToolkit().getScreenInsets(component.getGraphicsConfiguration());
	}

	private static String[] windowsFonts = new String[] { "Microsoft YaHei UI", "Microsoft YaHei", "微软雅黑", "宋体" };
	private static String[] macosFonts = new String[] { "PingHei", "PingFang SC", "Helvetica Neue", "Helvetica",
			"STHeitiSC-Light", "Arial", "sans-serif" };
	private static String[] linuxFonts = new String[] { "WenQuanYi Micro Hei", "Sans-serif" };

	private static void initDefaultFontName() {
		String[] fonts = null;
		GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] availableFontFamilyNames = g.getAvailableFontFamilyNames();
		if (isWindows()) {
			fonts = windowsFonts;
			defaultFontName = "微软雅黑";
		} else if (isMac()) {
			fonts = macosFonts;
			defaultFontName = "STHeitiSC-Light";
		} else {
			fonts = linuxFonts;
			defaultFontName = "Sans-serif";
		}
		for (String font : fonts) {
			for (String availableFont : availableFontFamilyNames) {
				if (font.equals(availableFont)) {
					defaultFontName = font;
					return;
				}
			}
		}
	}

	static {
		initDefaultFontName();
	}

	public static String getDefaultFontName() {
		return defaultFontName;
	}

	public static void setUIFont(String fontName, int fontStyle, int fontSize) {
		FontUIResource fontUIResource = new FontUIResource(fontName, fontStyle, fontSize);
		java.util.Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value != null && value instanceof javax.swing.plaf.FontUIResource)
				UIManager.put(key, fontUIResource);
		}
	}

	public static Font getFont(int fontStyle, int fontSize) {
		return new Font(getDefaultFontName(), fontStyle, fontSize);
	}

	public static void disposeAfter(Window window, int timeoutMs) {
		final WeakReference<Window> windowRef = new WeakReference<Window>(window);
		new Timer(timeoutMs, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				((Timer) e.getSource()).stop();
				Window win = windowRef.get();
				if (win != null && win.isVisible()) {
					win.dispose();
				}
			}
		}).start();
	}

	public static Font defaultSmallFont = getFont(Font.PLAIN, 12);

	public static Font defaultMiddleFont = getFont(Font.PLAIN, 14);

	public static Font defaultLargeFont = getFont(Font.PLAIN, 16);

	public static Color defaultTextColor = new Color(0x33, 0x33, 0x33);

	public static Color grayTextColor = new Color(0xb8, 0xb8, 0xb8);

	public static Color separatorColor = new Color(222, 222, 222);

	public static final Color btnDefaultColor = hex2Color("#333333");
	public static final Color btnDefaultBg = hex2Color("#ffffff");
	public static final Color btnDefaultBorder = hex2Color("#d1d1d1");

	public static final Color btnPrimaryColor = hex2Color("#ffffff");
	public static final Color btnPrimaryBg = hex2Color("#439fe0");
	public static final Color btnPrimaryBorder = hex2Color("#439fe0");

	public static final Color btnSuccessColor = hex2Color("#ffffff");
	public static final Color btnSuccessBg = hex2Color("#5cb85c");
	public static final Color btnSuccessBorder = darken(btnSuccessBg, .05f);

	public static final Color btnInfoColor = hex2Color("#ffffff");
	public static final Color btnInfoBg = hex2Color("#5bc0de");
	public static final Color btnInfoBorder = darken(btnInfoBg, .05f);

	public static final Color btnWarningColor = hex2Color("#ffffff");
	public static final Color btnWarningBg = hex2Color("#f0ad4e");
	public static final Color btnWarningBorder = darken(btnWarningBg, .05f);

	public static final Color btnDangerColor = hex2Color("#ffffff");
	public static final Color btnDangerBg = hex2Color("#d9534f");
	public static final Color btnDangerBorder = darken(btnDangerBg, .05f);

	/**
	 * 将rgb颜色值转换成16进制
	 *
	 * @param color
	 * @return
	 */
	public static String color2Hex(Color color) {
		String R = Integer.toHexString(color.getRed());
		R = R.length() < 2 ? ('0' + R) : R;
		String B = Integer.toHexString(color.getBlue());
		B = B.length() < 2 ? ('0' + B) : B;
		String G = Integer.toHexString(color.getGreen());
		G = G.length() < 2 ? ('0' + G) : G;
		return '#' + R + G + B;
	}

	/**
	 * 将16进制颜色值#123abc转换成rbg形式
	 *
	 * @param hex
	 * @return
	 */
	public static Color hex2Color(String hex) {
		if (hex.startsWith("#")) {
			hex = hex.substring(1);
		}
		return new Color(Integer.parseInt(hex, 16));
	}

	/**
	 * 将颜色亮度降低一定的值。参考less函数：darken。
	 *
	 * @param color
	 * @param rate
	 *            from [0,1]
	 * @return
	 */
	public static Color darken(Color color, float rate) {
		HSL hsl = RGB2HSL(color);
		hsl.luminance -= rate * 100;
		if (hsl.luminance < 0) {
			hsl.luminance = 0;
		}
		return HSL2RGB(hsl);
	}

	/**
	 * 将颜色亮度提高一定的值。参考less函数：lighten。
	 *
	 * @param color
	 * @param rate
	 *            from [0,1]
	 * @return
	 */
	public static Color lighten(Color color, float rate) {
		HSL hsl = RGB2HSL(color);
		hsl.luminance += rate * 100;
		if (hsl.luminance > 100) {
			hsl.luminance = 100;
		}
		return HSL2RGB(hsl);
	}

	/**
	 * HSL 颜色空间
	 */
	public static class HSL {
		float hue; // [0,360]
		float saturation; // [0,100]
		float luminance; // [0,100]

		public HSL() {
		}

		public HSL(float hue, float saturation, float luminance) {
			this.hue = hue;
			this.saturation = saturation;
			this.luminance = luminance;
		}
	}

	/**
	 * rgb颜色转成HSL颜色 HSL hue; // [0,360] saturation; // [0,100] luminance; //
	 * [0,100]
	 *
	 * @param rgb
	 * @return
	 */
	public static HSL RGB2HSL(Color rgb) {
		float r = rgb.getRed() / 255.0f;
		float b = rgb.getBlue() / 255.0f;
		float g = rgb.getGreen() / 255.0f;
		float h = 0, s = 0, l = 0;
		// normalizes red-green-blue va
		float maxVal = Math.max(r, Math.max(g, b));
		float minVal = Math.min(r, Math.min(g, b));

		// hue
		if (maxVal == minVal) {
			h = 0; // undefined
		} else if (maxVal == r && g >= b) {
			h = 60.0f * (g - b) / (maxVal - minVal);
		} else if (maxVal == r && g < b) {
			h = 60.0f * (g - b) / (maxVal - minVal) + 360.0f;
		} else if (maxVal == g) {
			h = 60.0f * (b - r) / (maxVal - minVal) + 120.0f;
		} else if (maxVal == b) {
			h = 60.0f * (r - g) / (maxVal - minVal) + 240.0f;
		}

		// luminance
		l = (maxVal + minVal) / 2.0f;

		// saturation
		if (l == 0 || maxVal == minVal) {
			s = 0;
		} else if (0 < l && l <= 0.5f) {
			s = (maxVal - minVal) / (maxVal + minVal);
		} else if (l > 0.5f) {
			s = (maxVal - minVal) / (2 - (maxVal + minVal)); // (maxVal-minVal >
																// 0)?
		}

		HSL hsl = new HSL();
		hsl.hue = (h > 360) ? 360 : ((h < 0) ? 0 : h);
		hsl.saturation = ((s > 1) ? 1 : ((s < 0) ? 0 : s)) * 100;
		hsl.luminance = ((l > 1) ? 1 : ((l < 0) ? 0 : l)) * 100;
		// Java浮点运算丢失精度，所以取整
		hsl.hue = (int) (hsl.hue + 0.5);
		hsl.saturation = (int) (hsl.saturation + 0.5);
		hsl.luminance = (int) (hsl.luminance + 0.5);
		return hsl;
	}

	/**
	 * 将HSL颜色空间转换成RGB颜色空间
	 *
	 * @return
	 */
	public static Color HSL2RGB(HSL hsl) {
		float h = hsl.hue;
		float s = hsl.saturation / 100.f;
		float l = hsl.luminance / 100.f;
		float R, G, B;
		if (s == 0) {
			R = G = B = l * 255.0f;
		} else {
			float q = (l < 0.5f) ? (l * (1.0f + s)) : (l + s - (l * s));
			float p = (2.0f * l) - q;
			float Hk = h / 360.0f;
			float[] T = new float[3];
			T[0] = Hk + 0.3333333f;
			T[1] = Hk;
			T[2] = Hk - 0.3333333f;
			for (int i = 0; i < 3; i++) {
				if (T[i] < 0)
					T[i] += 1.0f;
				if (T[i] > 1)
					T[i] -= 1.0f;
				if ((T[i] * 6) < 1) {
					T[i] = p + ((q - p) * 6.0f * T[i]);
				} else if ((T[i] * 2.0f) < 1) {
					T[i] = q;
				} else if ((T[i] * 3.0f) < 2) {
					T[i] = p + (q - p) * ((2.0f / 3.0f) - T[i]) * 6.0f;
				} else
					T[i] = p;
			}
			R = T[0] * 255.0f;
			G = T[1] * 255.0f;
			B = T[2] * 255.0f;
		}

		return new Color(Math.round(R), Math.round(G), Math.round(B));
	}

	/**
	 * 把图片进行缩放
	 *
	 * @param image
	 * @param width
	 * @param height
	 * @return
	 */
	public static Image resize(Image image, int width, int height) {
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
		Graphics2D g2d = (Graphics2D) bi.createGraphics();
		g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
		g2d.drawImage(image, 0, 0, width, height, null);
		g2d.dispose();
		return bi;
	}
}
