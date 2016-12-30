		
/*		
		
		// Iterator to create the IPC8 Mapping for Occurences
		Iterator<String> keySetIterator = IPC8Map.keySet().iterator();
		int PatentCounter = 0;
		while(keySetIterator.hasNext()){
			String key = keySetIterator.next();
	    	System.out.println(key + "     Occurences: " + IPC8Map.get(key));
	    	PatentCounter = PatentCounter +IPC8Map.get(key);
	    }
		
		
		// Iterator for Dates associated to a MasterPN
		Iterator<String> keySetIteratorDateMap = DateMap.keySet().iterator();
		
		
		while(keySetIteratorDateMap.hasNext()){
			String key = keySetIteratorDateMap.next();
			
			
			int firstRecordedDate = 999999999 ;
			for(int i = 0; i <= DateMap.get(key).size()-1; i++){
				int currentValue = DateMap.get(key).get(i);
				if( currentValue <= firstRecordedDate){
					firstRecordedDate = currentValue;
				} else {
					
				}
			DateMap.get(key).clear();
			DateMap.get(key).add(firstRecordedDate);
			}
			
			
			
			System.out.println(key + "     Associated Dates: " + DateMap.get(key));
	    }
		
		
		System.out.println("Total amount of patents: " + DateMap.size() );
		System.out.println("Different areas of patents: "  + IPC8Map.size());
		System.out.println("Total amount of signed in patents (one patent can be singed in multiple patent areas): "  + PatentCounter);
		
		
		
		
		
		
		
		HashMap<Integer, Integer> PubPerYear = new HashMap<Integer, Integer>();
		Iterator<String> keySetOpt = DateMap.keySet().iterator();
		int year = 4;
		
		while(keySetOpt.hasNext()){
			String key  = keySetOpt.next();
			List<Integer> dateer = DateMap.get(key);
			
			if(dateer.size()>0){
			int daterisch = dateer.get(0);
				if(daterisch > 0){
					daterisch = Integer.parseInt((""+ daterisch ).substring(0,year));
				}
				if(PubPerYear.containsKey(daterisch)){
					int counter = PubPerYear.get(daterisch) + 1 ;
					PubPerYear.put(daterisch, counter);
				} else {
					PubPerYear.put(daterisch, 1); 
				}
			}

		}
		
		
		Iterator<Integer> keySetOptPrint = PubPerYear.keySet().iterator();
		
		while(keySetOptPrint.hasNext()){
			int key = keySetOptPrint.next();
			if(key > 2000){
				System.out.println("Year : " + key + "     Patents in the year : " + PubPerYear.get(key));
			}
		
		}
*/		