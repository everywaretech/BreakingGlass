/*
 * Copyright 2014 Everyware Technologies S.L.
 * Author: Tom‡s Ruiz-L—pez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.everywaretech.breakingglass;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;

import com.google.android.glass.timeline.DirectRenderingCallback;
import com.google.android.glass.timeline.LiveCard;

import es.everywaretech.breakingglass.BreakingView.IBreakingListener;

public class BreakingDrawer implements DirectRenderingCallback {

	protected static final String TAG = "BreakingDrawer";
	
	protected BreakingView view = null;
	protected boolean renderingPaused = true;
	protected SurfaceHolder holder = null;
	
	protected IBreakingListener listener = new IBreakingListener() {
		
		@Override
		public void onTick() {
			
			if(holder != null){
				draw(view);
			}
			
		}
	};
	
	public BreakingDrawer(Context context){
		view = new BreakingView(context);
		view.setChangeListener(listener);
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
		renderingPaused = false;
        this.holder = holder;
        updateRenderingState();
        
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
		// Measure and layout the view with the canvas dimensions.
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);

        view.measure(measuredWidth, measuredHeight);
        view.layout(
                0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		this.holder = null;
        updateRenderingState();
	}

	@Override
	public void renderingPaused(SurfaceHolder holder, boolean paused) {
		renderingPaused = paused;
        updateRenderingState();
	}

	/**
     * Starts or stops rendering according to the {@link LiveCard}'s state.
     */
    private void updateRenderingState() {
        if (holder != null && !renderingPaused) {
            view.start();
        } else {
            view.stop();
        }
    }
    
    /**
     * Draws the view in the SurfaceHolder's canvas.
     */
    private void draw(View view) {
        Canvas canvas;
        try {
            canvas = holder.lockCanvas();
        } catch (Exception e) {
            Log.e(TAG, "Unable to lock canvas: " + e);
            return;
        }
        if (canvas != null) {
            view.draw(canvas);
            holder.unlockCanvasAndPost(canvas);
        }
    }
}
