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

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardScrollAdapter;

public class RecipeAdapter extends CardScrollAdapter{

	protected List<Card> cards = null;
	
	public RecipeAdapter(List<Card> cards){
		
		this.cards = cards;
		
	}

	@Override
	public int getCount() {

		return cards.size();
		
	}

	@Override
	public Object getItem(int position) {

		return cards.get(position);
		
	}

	@Override
	public int getPosition(Object obj) {

		for(int i = 0; i < cards.size(); i++){
			if(obj.equals(cards.get(i))){
				return i;
			}
		}
		
		return AdapterView.INVALID_POSITION;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		return cards.get(position).getView(convertView, parent);
		
	}
	
	@Override
    public int getViewTypeCount() {
        return Card.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position){
        return cards.get(position).getItemViewType();
    }


	
}
