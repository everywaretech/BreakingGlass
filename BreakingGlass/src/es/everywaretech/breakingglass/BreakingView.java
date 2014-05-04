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

import java.util.Random;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BreakingView extends RelativeLayout {

	protected IBreakingListener changeListener = null;
	
	protected TextView cookingText = null;
	protected boolean running = false;
	protected int numCookers = 0;
	protected String baseStr = ""; 
	
	protected final Handler handler = new Handler();
	protected final Random random = new Random(); 
	protected Runnable updateView = new Runnable() {
		
		@Override
		public void run() {
			if(running){
				updateText();
				postDelayed(updateView, getDelay());
			}
		}
	};
	
	public interface IBreakingListener{
		public void onTick();
	}
	
	public BreakingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.breaking_view, this);
		
		cookingText = (TextView) findViewById(R.id.cooking_text);
		numCookers = 1000 + random.nextInt(200);
		baseStr = context.getResources().getString(R.string.currently_cooking);
	}

	public BreakingView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BreakingView(Context context) {
		this(context, null, 0);
	}

	/**
     * Starts the chronometer.
     */
    public void start() {
        if (!running) {
            postDelayed(updateView, getDelay());
        }
        running = true;
    }

    /**
     * Stops the chronometer.
     */
    public void stop() {
        if (running) {
            removeCallbacks(updateView);
        }
        running = false;
    }
	
	public void setChangeListener(IBreakingListener listener){
		
		changeListener = listener;
		
	}

	@Override
    public boolean postDelayed(Runnable action, long delayMillis) {
        return handler.postDelayed(action, delayMillis);
    }
	
	protected long getDelay(){
		
		long delay = 500 + random.nextInt(2000);
		return delay;
		
	}
	
    protected void updateText() {
        
    	numCookers += random.nextInt(15);
    	cookingText.setText(String.format(baseStr, numCookers));
    	
        if (changeListener != null) {
            changeListener.onTick();
        }
        
    }
}
