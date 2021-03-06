/*
 * Copyright 2014-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package griffon.plugins.coverflow.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * The original code is from Romain Guy's example "A Music Shelf in Java2D".
 * It can be found here:
 * <p/>
 * http://www.curious-creature.org/2005/07/09/a-music-shelf-in-java2d/
 * <p/>
 * Updated Code
 * This code has been updated by Kevin Long (codebeach.com) to make it more
 * generic and more component like.
 * <p/>
 * History:
 * <p/>
 * 2/17/2008
 * ---------
 * - Removed CD case drawing
 * <p/>
 * 2/9/2011
 * --------
 * - Removed fixed sizes
 *
 * @author Romain.Guy
 * @author Kevin.Long
 * @author Alexander.Klein
 */
public class CrystalCaseFactory {

    private static CrystalCaseFactory instance = null;

    public static CrystalCaseFactory getInstance() {
        if (instance == null) {
            instance = new CrystalCaseFactory();
        }
        return instance;
    }

    public BufferedImage createCrystalCase(Image cover) {
        int imageWidth = cover.getWidth(null);
        int imageHeight = cover.getHeight(null);
        BufferedImage crystal = new BufferedImage(imageWidth,
            imageHeight,
            BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = crystal.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
            RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        int width = cover.getWidth(null);
        int height = cover.getHeight(null);

        float scale;

        if (width > height) {
            scale = (float) imageWidth / (float) width;
        } else {
            scale = (float) imageHeight / (float) height;
        }

        int scaledWidth = (int) ((float) width * scale);
        int scaledHeight = (int) ((float) height * scale);

        int x = (imageWidth - scaledWidth) / 2;
        int y = (imageHeight - scaledHeight) / 2;

        g2.drawImage(cover, x, y, scaledWidth, scaledHeight, null);

        g2.dispose();

        return crystal;
    }

    public BufferedImage createReflectedPicture(BufferedImage item) {
        return createReflectedPicture(item, createGradientMask(item.getWidth(), item.getHeight()));
    }

    public BufferedImage createReflectedPicture(BufferedImage item,
                                                BufferedImage alphaMask) {
        int itemWidth = item.getWidth();
        int itemHeight = item.getHeight();

        BufferedImage buffer = createReflection(item,
            itemWidth, itemHeight);

        applyAlphaMask(buffer, alphaMask, itemWidth, itemHeight);

        return buffer;
    }

    private void applyAlphaMask(BufferedImage buffer,
                                BufferedImage alphaMask,
                                int itemWidth, int itemHeight) {

        Graphics2D g2 = buffer.createGraphics();
        g2.setComposite(AlphaComposite.DstOut);
        g2.drawImage(alphaMask, null, 0, itemHeight);
        g2.dispose();
    }

    private BufferedImage createReflection(BufferedImage item,
                                           int itemWidth,
                                           int itemHeight) {

        BufferedImage buffer = new BufferedImage(itemWidth, itemHeight << 1,
            BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = buffer.createGraphics();

        g.drawImage(item, null, null);
        g.translate(0, itemHeight << 1);

        AffineTransform reflectTransform = AffineTransform.getScaleInstance(1.0, -1.0);
        g.drawImage(item, reflectTransform, null);
        g.translate(0, -(itemHeight << 1));

        g.dispose();

        return buffer;
    }

    public BufferedImage createGradientMask(int itemWidth, int itemHeight) {
        BufferedImage gradient = new BufferedImage(itemWidth, itemHeight,
            BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = gradient.createGraphics();
        GradientPaint painter = new GradientPaint(0.0f, 0.0f,
            new Color(1.0f, 1.0f, 1.0f, 0.5f),
            0.0f, itemHeight / 2.0f,
            new Color(1.0f, 1.0f, 1.0f, 1.0f));
        g.setPaint(painter);
        g.fill(new Rectangle2D.Double(0, 0, itemWidth, itemHeight));

        g.dispose();

        return gradient;
    }
}
