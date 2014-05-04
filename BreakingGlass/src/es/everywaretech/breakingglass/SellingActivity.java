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

import com.google.android.glass.app.Card;

import android.app.Activity;
import android.os.Bundle;

public class SellingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Card card = new Card(this);
		card.setImageLayout(Card.ImageLayout.LEFT);
		card.setText(R.string.pinkman_01);
		card.setTimestamp(R.string.just_now);
		card.addImage(R.drawable.pinkman);
		setContentView(card.getView());
		
	}

}
