package com.example.astarttest;

import android.graphics.Point;

public class AStartNode extends Point {
	private int mG = 0;
	private int mH = 0;
	private int mF = 0;

	private AStartNode mParent;
	private AStartNode mTarget;

	public AStartNode(int x, int y, AStartNode parent, AStartNode target) {
		super(x, y);
		mParent = parent;
		mTarget = target;
		setG();
		setH();
		setF();
	}


	public AStartNode(AStartNode p) {
		this(p.x, p.y, p.getParent(), p.getTarget());
	}


	private final void setG() {
		if (mParent == null)
			return;

		if (mParent.x != x && mParent.y != y)
			mG = mParent.getG() + 14;
		else
			mG = mParent.getG() + 10;
	}


	private final void setH() {
		if (mTarget == null)
			mH = 0;
		else
			mH = (Math.abs(mTarget.x - x) + Math.abs(mTarget.y - y)) * 10; 
	}


	private final void setF() {
		mF = mG + mH;
	}


	public final void setParent(AStartNode parent) {
		mParent = parent;
		setG();
		setH();
		setF();
	}


	public final AStartNode getParent() {
		return mParent;
	}


	private AStartNode getTarget() {
		return mTarget;
	}


	public final int getG() {
		return mG;
	}


	public final int getG(AStartNode p) {
		int g = -1;
		if (p != null) {
			g = p.getG() + ((p.x != x && p.y != y) ? 14 : 10);
		}
		return g;
	}


	public final int getH() {
		return mH;
	}

	
	public final int getF() {
		return mF;
	}
}
