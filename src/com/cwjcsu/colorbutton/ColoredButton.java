package com.cwjcsu.colorbutton;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

/**
 * 简单的颜色按钮
 * Created by cwjcsu@126.com on 15-12-9.
 */
public class ColoredButton extends JButton {
    private ButtonStyle buttonStyle;

    public ColoredButton(String label, ButtonStyle buttonStyle) {
        this(label, buttonStyle, true);
    }

    public ColoredButton(String label, ButtonStyle buttonStyle, boolean enterKeyAsClick) {
        super(label);
        setFont(GuiUtil.defaultMiddleFont);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setDefaultCapable(false);
        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setFocusable(true);
        this.buttonStyle = buttonStyle;
        if (enterKeyAsClick) {
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    if (e.getKeyChar() == KeyEvent.VK_ENTER && ColoredButton.this.hasFocus()) {
                        ColoredButton.this.doClick();
                    }
                }
            });
        }
    }


    @Override
    public void paint(Graphics g) {
        ColorStyle colorStyle = buttonStyle.colorStyle;
        if (hasFocus()) {
            colorStyle = buttonStyle.focusStyle;
        }
        if (getModel().isRollover()) {
            colorStyle = buttonStyle.hoverStyle;
        }
        if (getModel().isPressed()) {
            colorStyle = buttonStyle.activeStyle;
        }
        g.setColor(colorStyle.backgroundColor);
        g.fillRect(1, 1, getWidth() - 2, getHeight() - 2);
        g.setColor(colorStyle.borderColor);
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 4, 4);
        setForeground(colorStyle.color);
        super.paint(g);
    }

    public static class ColorStyle {
        Color color;//文字颜色
        Color backgroundColor;//背景色
        Color borderColor;//边框颜色
    }

    public static class ButtonStyle {
        //默认颜色方案
        ColorStyle colorStyle;
        //鼠标over的颜色方案
        ColorStyle hoverStyle;
        //获取焦点的颜色方案
        ColorStyle focusStyle;
        //按住的颜色方案
        ColorStyle activeStyle;
    }


    /*
        参加代码：bootstrap\less\buttons.less里面有对button的定义
     */
    public static final ButtonStyle btnDefault = createButtonStyle(GuiUtil.btnDefaultColor, GuiUtil.btnDefaultBg, GuiUtil.btnDefaultBorder, GuiUtil.hex2Color("#ebebeb"), GuiUtil.hex2Color("#666666"));

    public static final ButtonStyle btnPrimary = createButtonStyle(GuiUtil.btnPrimaryColor, GuiUtil.btnPrimaryBg, GuiUtil.btnPrimaryBorder, GuiUtil.hex2Color("#63b4ee"), GuiUtil.hex2Color("#0a53a5"));

    public static final ButtonStyle btnSuccess = createButtonStyle(GuiUtil.btnSuccessColor, GuiUtil.btnSuccessBg, GuiUtil.btnSuccessBorder, GuiUtil.hex2Color("#8cd53b"), GuiUtil.hex2Color("#2b782c"));

    public static final ButtonStyle btnInfo = createButtonStyle(GuiUtil.btnInfoColor, GuiUtil.btnInfoBg, GuiUtil.btnInfoBorder, GuiUtil.lighten(GuiUtil.btnInfoBg, .1f), GuiUtil.darken(GuiUtil.btnInfoBg, .1f));

    public static final ButtonStyle btnWarning = createButtonStyle(GuiUtil.btnWarningColor, GuiUtil.btnWarningBg, GuiUtil.btnWarningBorder, GuiUtil.lighten(GuiUtil.btnWarningBg, .1f), GuiUtil.darken(GuiUtil.btnWarningBg, .1f));

    public static final ButtonStyle btnDanger = createButtonStyle(GuiUtil.btnDangerColor, GuiUtil.btnDangerBg, GuiUtil.btnDangerBorder, GuiUtil.hex2Color("#f36552"), GuiUtil.hex2Color("#8b0b0b"));


    public static ButtonStyle createButtonStyle(Color color, Color background, Color border, Color hoverBg, Color activeBg) {
        ButtonStyle bs = new ButtonStyle();
        ColorStyle colorStyle = new ColorStyle();
        colorStyle.color = color;
        colorStyle.backgroundColor = background;
        colorStyle.borderColor = border;
        bs.colorStyle = colorStyle;

        ColorStyle focusStyle = new ColorStyle();
        focusStyle.color = color;
        focusStyle.backgroundColor = GuiUtil.darken(background, 0.1f);
        focusStyle.borderColor = GuiUtil.darken(border, .25f);
        bs.focusStyle = focusStyle;

        ColorStyle hoverStyle = new ColorStyle();
        hoverStyle.color = color;
        hoverStyle.backgroundColor = hoverBg;
        hoverStyle.borderColor = border;
        bs.hoverStyle = hoverStyle;

        ColorStyle activeStyle = new ColorStyle();
        activeStyle.color = GuiUtil.hex2Color("#FFFFFF");
        activeStyle.backgroundColor = activeBg;
        activeStyle.borderColor = activeBg;
        bs.activeStyle = activeStyle;

        return bs;
    }
}
