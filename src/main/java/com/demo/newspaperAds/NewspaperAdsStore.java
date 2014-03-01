package com.demo.newspaperAds;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;


public class NewspaperAdsStore {

    private final Map<String, Set<String>> adsToNewspaperMap;
    private final Map<String, Set<String>> newspaperToAdsMap;
    
    public NewspaperAdsStore() {
        this.newspaperToAdsMap = Maps.newHashMap();
        this.adsToNewspaperMap = Maps.newHashMap();
    }
    
    public void createNewspaper(String newspaperId) {
        if (!newspaperToAdsMap.containsKey(newspaperId)) {
            Set<String> emptyAds = Sets.newHashSet();
            newspaperToAdsMap.put(newspaperId, emptyAds);
        }
    }

    public Set<String> getAllNewspapers() {
        return ImmutableSet.copyOf((newspaperToAdsMap.keySet()));
    }

    public void createAd(String adId) {
        if (!adsToNewspaperMap.containsKey(adId)) {
            Set<String> emptyAds = Sets.newHashSet();
            adsToNewspaperMap.put(adId, emptyAds);
        }
    }
    
    public Set<String> getAllAds() {
        return ImmutableSet.copyOf((adsToNewspaperMap.keySet()));
    }
    
    public void insertAd(String newspaperId, String adId) {
        checkNewspaperExists(newspaperId);
        checkAdExists(adId);
        newspaperToAdsMap.get(newspaperId).add(adId);
        adsToNewspaperMap.get(adId).add(newspaperId);
    }

    public Set<String> getAllAdsForNewspaper(String newspaperId) {
        checkNewspaperExists(newspaperId);
        return ImmutableSet.copyOf(newspaperToAdsMap.get(newspaperId));
    }
    
    public Set<String> getAllNewspapersForAd(String adId) {
        checkAdExists(adId);
        return ImmutableSet.copyOf(adsToNewspaperMap.get(adId));
    }
    
    private void checkNewspaperExists(String newspaperId) {
        if (!newspaperToAdsMap.containsKey(newspaperId)) {
            throw new IllegalArgumentException(String.format("Newspaper not found: %s", newspaperId));
        }
    }

    private void checkAdExists(String adId) {
        if (!adsToNewspaperMap.containsKey(adId)) {
            throw new IllegalArgumentException(String.format("Ad not found: %s", adId));
        }
    }

}
