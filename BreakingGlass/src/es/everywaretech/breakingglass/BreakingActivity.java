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

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class BreakingActivity extends Activity {

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		
		openOptionsMenu();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.menu_breaking, menu);
		return true;
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_cook) {
			openRecipe();
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}

	protected void openRecipe(){
		
		Intent intent = new Intent(this, RecipeActivity.class);
		startActivity(intent);
		
	}
}
