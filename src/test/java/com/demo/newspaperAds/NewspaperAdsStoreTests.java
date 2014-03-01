package com.demo.newspaperAds;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class NewspaperAdsStoreTests {
    static final String NEWSPAPER_ID_1 = "NEWS 1";
    static final String NEWSPAPER_ID_2 = "NEWS 2";
    
    static final String AD_ID_1 = "AD 1";
    static final String AD_ID_2 = "AD 2";

    NewspaperAdsStore target;
    
    @Before
    public void setUp() throws Exception {
        target = new NewspaperAdsStore();
    }
    
    @Test
    public void initiallyHasNoNewspapers() throws Exception {
        assertThat(target.getAllNewspapers().isEmpty(), is(true));
    }
    
    @Test
    public void canCreate1Newspaper() throws Exception {
        target.createNewspaper(NEWSPAPER_ID_1);
        assertThat(target.getAllNewspapers(), hasItem(NEWSPAPER_ID_1));
    }
    
    @Test
    public void canCreateSeveralNewspapers() throws Exception {
        target.createNewspaper(NEWSPAPER_ID_1);
        assertThat(target.getAllNewspapers(), hasItem(NEWSPAPER_ID_1));
        
        target.createNewspaper(NEWSPAPER_ID_2);
        assertThat(target.getAllNewspapers(), hasItem(NEWSPAPER_ID_1));
        assertThat(target.getAllNewspapers(), hasItem(NEWSPAPER_ID_2));
    }
    
    @Test
    public void initiallyHasNoAds() throws Exception {
        assertThat(target.getAllAds().isEmpty(), is(true));
    }
    
    @Test
    public void canCreate1Ad() throws Exception {
        target.createAd(AD_ID_1);
        assertThat(target.getAllAds(), hasItem(AD_ID_1));
    }
    
    @Test
    public void canCreateSeveralAds() throws Exception {
        target.createAd(AD_ID_1);
        assertThat(target.getAllAds(), hasItem(AD_ID_1));
        
        target.createAd(AD_ID_2);
        assertThat(target.getAllAds(), hasItem(AD_ID_1));
        assertThat(target.getAllAds(), hasItem(AD_ID_2));
    }
    
    @Test
    public void cantCreateSameNewspaperMoreThanOnce() throws Exception {
        target.createNewspaper(NEWSPAPER_ID_1);
        target.createNewspaper(NEWSPAPER_ID_1);
        assertThat(target.getAllNewspapers(), hasItem(NEWSPAPER_ID_1));
    }
    
    @Test
    public void cantCreateSameAddMoreThanOnce() throws Exception {
        target.createAd(AD_ID_1);
        target.createAd(AD_ID_1);
        assertThat(target.getAllAds(), hasItem(AD_ID_1));
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void getAllAdsForNewspaperThatDoesntExistThrows() throws Exception {
        target.getAllAdsForNewspaper(NEWSPAPER_ID_1);
    }
    
    @Test
    public void getAllAdsForNewspaperWithNoAdsIsEmpty() {
        target.createNewspaper(NEWSPAPER_ID_1);
        assertThat(target.getAllAdsForNewspaper(NEWSPAPER_ID_1).isEmpty(), is(true));
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void insertAdWhenNewspaperDoesntExistThrows() throws Exception {
        target.createAd(AD_ID_1);
        target.insertAd(NEWSPAPER_ID_1, AD_ID_1);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void insertAdWhenAdDoesntExistThrows() throws Exception {
        target.createNewspaper(NEWSPAPER_ID_1);
        target.insertAd(NEWSPAPER_ID_1, AD_ID_1);
    }
    
    @Test
    public void canInsert1Ad() throws Exception {
        target.createNewspaper(NEWSPAPER_ID_1);
        target.createAd(AD_ID_1);
        target.insertAd(NEWSPAPER_ID_1, AD_ID_1);
        target.getAllAdsForNewspaper(NEWSPAPER_ID_1);
        assertThat(target.getAllAdsForNewspaper(NEWSPAPER_ID_1), hasItem(AD_ID_1));
    }
    
    @Test
    public void cantInsertSameAdMoreThanOnce() throws Exception {
        target.createNewspaper(NEWSPAPER_ID_1);
        target.createAd(AD_ID_1);
        target.insertAd(NEWSPAPER_ID_1, AD_ID_1);
        target.insertAd(NEWSPAPER_ID_1, AD_ID_1);
        target.getAllAdsForNewspaper(NEWSPAPER_ID_1);
        assertThat(target.getAllAdsForNewspaper(NEWSPAPER_ID_1), hasItem(AD_ID_1));
    }
    
    @Test
    public void canInsertSeveralAds() throws Exception {
        target.createNewspaper(NEWSPAPER_ID_1);
        target.createAd(AD_ID_1);
        target.createAd(AD_ID_2);
        target.insertAd(NEWSPAPER_ID_1, AD_ID_1);
        target.insertAd(NEWSPAPER_ID_1, AD_ID_2);
        Iterable<String> matchingAds = target.getAllAdsForNewspaper(NEWSPAPER_ID_1);
        assertThat(matchingAds, hasItem(AD_ID_1));
        assertThat(matchingAds, hasItem(AD_ID_2));
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void getNewspapersForAdWhenAdDoesntExistThrows() throws Exception {
        target.getAllNewspapersForAd(AD_ID_1);
    }
    
    @Test
    public void getNewspapersForAdWhenNeverInsertedReturnsEmpty() throws Exception {
        target.createAd(AD_ID_1);
        assertThat(target.getAllNewspapersForAd(AD_ID_1).isEmpty(), is(true));
    }
    
    @Test
    public void getNewspapersForAdWhenInsertedSeveralTimes() throws Exception {
        target.createAd(AD_ID_1);
        target.createNewspaper(NEWSPAPER_ID_1);
        target.createNewspaper(NEWSPAPER_ID_2);
        target.insertAd(NEWSPAPER_ID_1, AD_ID_1);
        target.insertAd(NEWSPAPER_ID_2, AD_ID_1);
        Iterable<String> matchingNewspapers = target.getAllNewspapersForAd(AD_ID_1);
        assertThat(matchingNewspapers, hasItem(NEWSPAPER_ID_1));
        assertThat(matchingNewspapers, hasItem(NEWSPAPER_ID_2));
    }
}
