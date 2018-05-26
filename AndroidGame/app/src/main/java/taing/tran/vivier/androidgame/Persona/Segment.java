package taing.tran.vivier.androidgame.Persona;

import android.graphics.Rect;

public class Segment {
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private double m;
    private double b;
    private boolean ishoriz;
    private boolean isvert;
    private static final int OFFSET = 2;


    public Segment(int x1s, int y1s, int x2s, int y2s) {
        if (x1s > x2s) {
            this.x1 = x2s;
            this.x2 = x1s;
        } else {
            this.x1 = x1s;
            this.x2 = x2s;
        }
        if (y1s > y2s) {
            this.y1 = y2s;
            this.y2 = y1s;
        } else {
            this.y1 = y1s;
            this.y2 = y2s;
        }
        int ydif = y2s - y1s;
        int xdif = x2s - x1s;
        if (ydif == 0) {
            this.ishoriz = true;
            this.m = 0;
            this.b = x1s;
        } else if (xdif == 0) {
            this.isvert = true;
        } else {
            this.m = (double) ydif / xdif;
            double r = (double) ydif / xdif;
            this.b = y1s - (r * x1s);
            this.isvert = false;
            this.ishoriz = false;
        }
    }

    public final boolean intersected(Segment s, Segment s2) {
        if (s.ishoriz && s2.ishoriz) {
            return false;
        }

        if (s.isvert && s2.isvert) {
            return false;
        }

        if (s.isvert) {
            int x = s.x1;
            if (s2.x1 <= x + 2 && s2.x2 + 2 >= x) {
                int y = (int) ((s.m * x) + s.b);
                if (s.y1 <= y + 2 && s.y2 + 2 >= y) {
                    if (s2.y1 <= y + 2 && s2.y2 + 2 >= y) {
                        return true;
                    }
                }
            }
            return false;
        }

        if (s2.isvert) {
            int x = s2.x1;
            if (s.x1 <= x + 2 && s.x2 + 2 >= x) {
                int y = (int) ((s.m * x) + s.b);
                if (s.y1 <= y + 2 && s.y2 + 2 >= y) {
                    if (s2.y1 <= y + 2 && s2.y2 + 2 >= y) {
                        return true;
                    }
                }
            }
            return false;
        }

        if (s.ishoriz) {
            int y = s.y1;
            if (s2.y1 <= y + 2 && s2.y2 + 2 >= y) {
                int x = (int) ((y - s.b) / s.m);
                if (s.x1 <= x + 2 && s.x2 + 2 >= x) {
                    if (s2.x1 <= x + 2 && s2.x2 + 2 >= x)
                        return true;
                }
                return false;
            }
        }

        if (s2.ishoriz) {
            int y = s2.y1;
            if (s.y1 <= y + 2 && s.y2 + 2 >= y) {
                int x = (int) ((y - s.b) / s.m);
                if (s.x1 <= x + 2 && s.x2 + 2 >= x) {
                    if (s2.x1 <= x + 2 && s2.x2 + 2 >= x)
                        return true;
                }
            }
            return false;
        }

        if (s.m == s2.m) {
            return false;
        }

        int x = (int) (s.m - s2.m);
        x = (int) ((s2.b - s.b) / x);
        int y = (int) ((x * s.m) + s.b);
        if (s.y1 <= y + OFFSET) {
            if (s.y2 + OFFSET >= y) {
                if (s2.y1 <= y + OFFSET) {
                    if (s2.y2 + OFFSET >= y) {
                        if (s.x1 <= x + OFFSET) {
                            if (s.x2 + OFFSET >= x) {
                                if (s2.x1 <= x + OFFSET) {
                                    if (s2.x2 + OFFSET >= x) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }


    public static boolean intersects2(Segment s, Rect r) {
        Segment top = new Segment(r.left, r.top, r.right, r.top);
        Segment left = new Segment(r.left, r.top, r.left, r.bottom);
        Segment bottom = new Segment(r.left, r.bottom, r.right, r.bottom);
        Segment right = new Segment(r.right, r.top, r.right, r.bottom);
        if (s.intersected(s, top)) {
            return true;
        }
        if (s.intersected(s, left)) {
            return true;
        }
        if (s.intersected(s, bottom)) {
            return true;
        }
        if (s.intersected(s, right)) {
            return true;
        }
        return false;
    }
}
