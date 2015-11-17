package com.example.astarttest;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class AStartView extends SurfaceView implements Callback, Runnable {
	int F = 0;
	int H = 0;
	int G = 0;
	int tileW = 5;
	int tileH = 5;
	int tileXNum = 0;
	int tileYNum = 0;
	int[][] allTiles;
    long startTime = 0;
	boolean isFind = false;
	boolean isExit = false;
	AStartNode targetNode;
	AStartNode currentNode;
	ArrayList<AStartNode> openList = new ArrayList<AStartNode>();
	ArrayList<AStartNode> closeList = new ArrayList<AStartNode>();

	public AStartView(Context context) {
		super(context);
		int width = ((Activity)context).getResources().getDisplayMetrics().widthPixels;
		int height = ((Activity) context).getResources().getDisplayMetrics().heightPixels;
		tileXNum = width / tileW;
		tileYNum = height / tileH;
		allTiles = new int[tileXNum][tileYNum];
        targetNode = new AStartNode(800 / tileW, 800 / tileH, null, null);
        currentNode = new AStartNode(50 / tileW, 50 / tileH, null, targetNode);

		allTiles[targetNode.x][targetNode.y] = 2;
		for (int i = 1; i < 30; i++) {
			allTiles[20][1+i] = 1;
		}
		allTiles[currentNode.x][currentNode.y] = 3;
		initPos();
		getHolder().addCallback(this);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(!isExit) {
//                    myLogic();
//                }
//            }
//        }).start();
        startTime = System.currentTimeMillis();
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		new Thread(this).start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		isExit = true;
	}

	@Override
	public void run() {
		try{
		while(!isExit) {
            myLogic();
            myDraw();
            //Thread.sleep(20);
		}
		}catch(Throwable e) {
			Log.e("?", "?", e);
		}
	}

	private void myDraw() {
		Paint pa = new Paint();
        //if (!isFind) return;
		Canvas c = getHolder().lockCanvas();
        //c.drawColor(Color.BLACK);
		if (c != null) {
			for (int i = 0; i < tileXNum; i++) {
				for (int j = 0; j < tileYNum; j++) {
					switch(allTiles[i][j]) {
						case 0:
							pa.setColor(Color.WHITE);
							break;
						case 1:
							pa.setColor(Color.BLUE);
							break;
						case 2:
							pa.setColor(Color.RED);
							break;
						case 3:
							pa.setColor(Color.GREEN);
							break;
					}

					c.drawRect(i * tileW+1, j * tileH+1, i * tileW + tileW, j * tileH + tileH, pa);
				}
			}
			pa.setColor(Color.argb(100, 99, 99, 99));
			for (AStartNode p : openList) {
				if (p.x >= 0 && p.y >= 0 && p.x < tileXNum && p.y < tileYNum && allTiles[p.x][p.y] != 1) {
					c.drawRect(p.x*tileW+1, p.y*tileH+1, p.x * tileW + tileW, p.y * tileH + tileH, pa);
				}
			}
		
			//openList.clear();
			if (isFind) {
				pa.setColor(Color.YELLOW);
				AStartNode node = targetNode;
				do {
					c.drawRect(node.x*tileW+1, node.y*tileH+1, node.x* tileW + tileW, node.y*tileH+tileH, pa);
					node = node.getParent();
				}while(node != null);
                isExit = true;
			}
			pa.setTextSize(50);
			c.drawText(F + "=" + G + "+" + H, 200, 1000, pa);
            c.drawText("Time: " + (System.currentTimeMillis() - startTime), 200, 1200, pa);
			getHolder().unlockCanvasAndPost(c);
		}
	}


	private void myLogic() {
		AStartNode mp = null;
		AStartNode cp = null;
		int f = -1;
		int s = 0;
		if (isFind) {
			return;
		}

		s = openList.size();
		for (int i = 0; i < s; i++) {
			cp = openList.get(i);
			if (cp != null) {
				if (f == -1) {
					f = cp.getF();
					mp = cp;
					continue;
				}
				if (f > cp.getF()) {
					f = cp.getF();
					mp = cp;
				}
			}
		}
		if (mp.equals(targetNode)) {
			targetNode.setParent(mp);
			isFind = true;
		}
		openList.remove(mp);
		closeList.add(mp);
		addPos(mp);
		currentNode = mp;
		F = mp.getF();
		G = mp.getG();
		H = mp.getH();
		allTiles[currentNode.x][currentNode.y] = 3; 
	}


	private void initPos() {
		AStartNode p = currentNode;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				int x = p.x + i;
				int y = p.y + j;
				if (x < 0 || y < 0 || x > tileXNum-1 || y > tileYNum-1 || (i == 0 && j == 0)
					|| allTiles[x][y] == 1 || closeList.contains(p))
					continue;
				openList.add(new AStartNode(x, y, p, targetNode));
			}
		}
		openList.remove(currentNode);
		closeList.add(currentNode);
	}


	private void addPos(AStartNode p) {
		int better = 0;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				int x = p.x + i;
				int y = p.y + j;
				AStartNode np = new AStartNode(x, y, p, targetNode);
				if (x < 0 || y < 0 || x > tileXNum-1 || y > tileYNum-1 || (i == 0 && j == 0)
					|| allTiles[x][y] == 1 || closeList.contains(np))
					continue;

				if (openList.contains(np)) {
					int mg = np.getG(p.getParent());
					int bg = np.getG();
					if (bg < mg) {
						openList.remove(np);
						openList.add(np);
					}
					continue;
				}
				openList.add(np);
			}
		}
	}
}
