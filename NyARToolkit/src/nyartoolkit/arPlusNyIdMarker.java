//package n

import processing.core.*;
import processing.video.*;
import jp.nyatla.nyar4psg.*;

public class arPlusNyIdMarker extends PApplet {

    /**
     * NyARToolkit for proce55ing/1.0.0 (c)2008-2011 nyatla
     * airmail(at)ebony.plala.or.jp
     *
     * AR\u30de\u30fc\u30ab\u3068Id\u30de\u30fc\u30ab\u3092\u540c\u6642\u306b\u4f7f\u3046\u4f8b\u3067\u3059\u3002
     * AR\u30de\u30fc\u30ab\u306fkanji,hiro\u3001id\u30de\u30fc\u30ab\u306f0,1\u756a\u306e\u30de\u30fc\u30ab\u3092\u4f7f\u3046\u4e8b\u304c\u3067\u304d\u307e\u3059\u3002
     *
     * This sample handles 2 ARToolkit style markers and 2 NyId markers at same
     * time. The ARToolKit marker files are kanji.patt and hiro.patt. NyId
     * marker ids are #0 and #1.
     */
    Capture cam;
    MultiMarker nya;
    PShape rocket;
    PShape xbox;

    public void setup() {
        size(640, 480, P3D);
        colorMode(RGB, 100);
        println(MultiMarker.VERSION);
        cam = new Capture(this, 640, 480);
        nya = new MultiMarker(this, width, height, "camera_para.dat", NyAR4PsgConfig.CONFIG_PSG);
        nya.addARMarker("patt.hiro", 80);//id=0
        nya.addARMarker("patt.kanji", 80);//id=1
        nya.addNyIdMarker(0, 80);//id=2
        nya.addNyIdMarker(1, 80);//id=3
        rocket = loadShape("models//rocket.obj");
        xbox = loadShape("models//cow.obj");
        rocket.scale(1);
        xbox.scale(20);
        cam.start();
    }

    public void draw() {
        if (cam.available() != true) {
            return;
        }
        cam.read();
        nya.detect(cam);
        background(0);
        nya.drawBackground(cam);//frustum\u3092\u8003\u616e\u3057\u305f\u80cc\u666f\u63cf\u753b
        for (int i = 0; i < 4; i++) {
            if ((!nya.isExistMarker(i))) {
                continue;
            }
            
            if (nya.isExistMarker(0)) {
                nya.beginTransform(0);
                shape(rocket);
                nya.endTransform();
            }
            if (nya.isExistMarker(1)) {
                nya.beginTransform(1);
                shape(xbox);
                nya.endTransform();
            }
            
            //System.out.println(nya.isExistMarker(1));
            
            //fill(100 * (((i + 1) / 4) % 2), 100 * (((i + 1) / 2) % 2), 100 * (((i + 1)) % 2));
            //shape(rocket);
            //translate(0, 0, 20);
            //box(40);
            
        }
    }

    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[]{"arPlusNyIdMarker"};
        if (passedArgs != null) {
            PApplet.main(concat(appletArgs, passedArgs));
        } else {
            PApplet.main(appletArgs);
        }
    }
}
