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

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.android.glass.app.Card;
import com.google.android.glass.media.Sounds;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

public class RecipeActivity extends Activity{

	protected CardScrollView scrollView = null;
	protected CardScrollAdapter adapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		adapter = new RecipeAdapter(createCards(this));
		scrollView = new CardScrollView(this);
		scrollView.setAdapter(adapter);
		
		scrollView.activate();
		
		setContentView(scrollView);
		setCardScrollerListener();
	}
	
	protected List<Card> createCards(Context context){
		
		ArrayList<Card> cards = new ArrayList<Card>();
		
		Card c1 = new Card(context);
		c1.setImageLayout(Card.ImageLayout.LEFT);
		c1.setText(R.string.step_01);
		c1.addImage(R.drawable.s01);
		cards.add(c1);
		
		Card c2 = new Card(context);
		c2.setImageLayout(Card.ImageLayout.LEFT);
		c2.setText(R.string.step_02);
		c2.setFootnote(R.string.step_02b);
		c2.addImage(R.drawable.s02);
		cards.add(c2);
		
		Card c3 = new Card(context);
		c3.setImageLayout(Card.ImageLayout.LEFT);
		c3.setText(R.string.step_03);
		c3.addImage(R.drawable.s03);
		c3.addImage(R.drawable.s04);
		c3.addImage(R.drawable.s05);
		cards.add(c3);
		
		Card c4 = new Card(context);
		c4.setImageLayout(Card.ImageLayout.LEFT);
		c4.setText(R.string.step_04);
		c4.setFootnote(R.string.step_04b);
		c4.addImage(R.drawable.s06);
		c4.addImage(R.drawable.s07);
		cards.add(c4);
		
		Card c5 = new Card(context);
		c5.setImageLayout(Card.ImageLayout.FULL);
		c5.setText(R.string.step_05);
		c5.addImage(R.drawable.s08);
		cards.add(c5);
		
		Card c6 = new Card(context);
		c6.setImageLayout(Card.ImageLayout.FULL);
		c6.setText(R.string.step_06);
		c6.setFootnote(R.string.step_06b);
		c6.addImage(R.drawable.s09);
		cards.add(c6);
		
		return cards;
		
	}
	
	/**
	* Different type of activities can be shown, when tapped on a card.
	*/
    protected void setCardScrollerListener() {
    	
        scrollView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                
                switch (position) {
                	case 5:
                		startSelling();
                		int soundEffect = Sounds.TAP;
                		// Play sound.
                        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                        am.playSoundEffect(soundEffect);
                		break;
                }

            }
        });
        
    }
    
    protected void startSelling(){
    	
    	Intent intent = new Intent(this, SellingBroadcastReceiver.class);
		PendingIntent pending = PendingIntent.getBroadcast(this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_CANCEL_CURRENT);
		
		AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5*1000, pending);
    	
    }
    
}
