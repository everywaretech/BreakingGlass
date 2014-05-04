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

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.google.android.glass.timeline.LiveCard;
import com.google.android.glass.timeline.LiveCard.PublishMode;

public class SellingService extends Service {

	private static final String LIVE_CARD_TAG = "SELLING_BREAKING_GLASS";

    private LiveCard liveCard;
    private RemoteViews liveCardView;

    private final Handler handler = new Handler();
    private final UpdateLiveCardRunnable mUpdateLiveCardRunnable =
        new UpdateLiveCardRunnable();
    private static final long DELAY_MILLIS = 30000;

    protected static final int[] sentences = new int[]{
    	R.string.pinkman_01,
    	R.string.pinkman_02,
    	R.string.pinkman_03
    };
    protected int currentSentence = 0;
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        
    	if (liveCard == null) {
            // Get an instance of a live card
            liveCard = new LiveCard(this, LIVE_CARD_TAG);

            // Inflate a layout into a remote view
            liveCardView = new RemoteViews(getPackageName(),
                R.layout.selling_view);

            // Set up initial RemoteViews values
            String sentence = getString(sentences[currentSentence]);
            liveCardView.setTextViewText(R.id.content, sentence);
            liveCardView.setImageViewResource(R.id.image, R.drawable.pinkman);
            liveCardView.setTextViewText(R.id.timestamp, getString(R.string.just_now));

            // Publish the live card
            liveCard.setViews(liveCardView);
            liveCard.publish(PublishMode.REVEAL);

            // Queue the update text runnable
            handler.post(mUpdateLiveCardRunnable);
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (liveCard != null && liveCard.isPublished()) {
            mUpdateLiveCardRunnable.setStop(true);

            liveCard.unpublish();
            liveCard = null;
        }
        super.onDestroy();
    }

    /**
     * Runnable that updates live card contents
     */
    private class UpdateLiveCardRunnable implements Runnable{

        private boolean mIsStopped = false;

        public void run(){
            if(!isStopped()){
            	currentSentence = (currentSentence + 1) % sentences.length;
            	String sentence = getString(sentences[currentSentence]);
                liveCardView.setTextViewText(R.id.content, sentence);
            	
            	handler.postDelayed(mUpdateLiveCardRunnable, DELAY_MILLIS);
            }
        }

        public boolean isStopped() {
            return mIsStopped;
        }

        public void setStop(boolean isStopped) {
            this.mIsStopped = isStopped;
        }
    }

    
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
