package net.machinemuse.general.geometry;

public class MuseRect {
	MusePoint2D ul;
	MusePoint2D br;

	public MuseRect(double left, double top, double right, double bottom, boolean growFromMiddle) {
		ul = new MusePoint2D(left, top);
		br = new MusePoint2D(right, bottom);
		if (growFromMiddle) {
			MusePoint2D center = ul.plus(br).times(0.5);
			this.ul = new FlyFromPointToPoint2D(center, ul, 200);
			this.br = new FlyFromPointToPoint2D(center, br, 200);
		}
	}

	public MuseRect(double left, double top, double right, double bottom) {
		this(left, top, right, bottom, false);
	}

	public MuseRect(MusePoint2D ul, MusePoint2D br) {
		this.ul = ul;
		this.br = br;
	}

	public double left() {
		return ul.x();
	}

	public double right() {
		return br.x();
	}

	public double top() {
		return ul.y();
	}

	public double bottom() {
		return br.y();
	}
}
