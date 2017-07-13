package termproject;

import javafx.scene.Group;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

public class Xform extends Group {

    public Translate t = new Translate();
    
    public Rotate rx = new Rotate();
    {
        rx.setAxis(Rotate.X_AXIS);
    }
    
    public Rotate ry = new Rotate();
    {
        ry.setAxis(Rotate.Y_AXIS);
    }
    
    public Rotate rz = new Rotate();
    {
        rz.setAxis(Rotate.Z_AXIS);
    }
    
    public Scale s = new Scale();

    public Xform() {
        super();
        getTransforms().addAll(t, rz, ry, rx, s);
    }

    public void setTx(double x) {
        t.setX(x);
    }

    public void setTy(double y) {
        t.setY(y);
    }

    public void setTz(double z) {
        t.setZ(z);
    }

    public void setRotateX(double x) {
        rx.setAngle(x);
    }

    public void setRotateY(double y) {
        ry.setAngle(y);
    }

    public void setRotateZ(double z) {
        rz.setAngle(z);
    }
    
    public void setScale(double x, double y, double z) {
        s.setX(x);
        s.setY(y);
        s.setZ(z);
    }

    @Override
    public String toString() {
        return "Xform[t = ("
                + t.getX() + ", "
                + t.getY() + ", "
                + t.getZ() + ")  "
                + "r = ("
                + rx.getAngle() + ", "
                + ry.getAngle() + ", "
                + rz.getAngle() + ")  ";
    }
}
