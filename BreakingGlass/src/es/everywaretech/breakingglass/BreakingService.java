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

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.android.glass.timeline.LiveCard;
import com.google.android.glass.timeline.LiveCard.PublishMode;

public class BreakingService extends Service {

    private static final String LIVE_CARD_TAG = "BREAKING_SERVICE";

    private BreakingDrawer callback;

    private LiveCard liveCard;
	
	@Override
	public IBinder onBind(Intent intent) { 
		return null;
	}

	@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (liveCard == null) {
            liveCard = new LiveCard(this, LIVE_CARD_TAG);

            // Keep track of the callback to remove it before unpublishing.
            callback = new BreakingDrawer(this);
            liveCard.setDirectRenderingEnabled(true).getSurfaceHolder().addCallback(callback);

            Intent menuIntent = new Intent(this, BreakingActivity.class);
            menuIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            liveCard.setAction(PendingIntent.getActivity(this, 0, menuIntent, 0));
            liveCard.attach(this);
            liveCard.publish(PublishMode.REVEAL);
        } else {
            liveCard.navigate();
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (liveCard != null && liveCard.isPublished()) {
            liveCard.unpublish();
            liveCard = null;
        }
        super.onDestroy();
    }
}
