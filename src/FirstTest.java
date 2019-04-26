import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;

public class FirstTest extends CoreTestCase {

    private MainPageObject MainPageObject;

    protected void setUp() throws Exception
    {
        super.setUp();

        MainPageObject = new MainPageObject(driver);
    }

    @Test
    public void testSearch()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();

    }

    @Test
    public void testCompareArticleTitle()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        String article_title = ArticlePageObject.getArticleTitle();
        assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                article_title
        );

    }

    @Test
    public void testLookForWordAndCancel()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("test");
        int size_of_list = SearchPageObject.getAmountOfFoundArticles();
        assertTrue("There is just 1 element on the list", size_of_list>1);
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForEmptySearchLabel();

    }

    @Test
    public void testSwipeArticle()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Appium");
        SearchPageObject.clickByArticleWithSubstring("Appium");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.swipeToFooter();

    }

    @Test
    public void testSaveFirstArticleToMyList()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();
        String article_title = ArticlePageObject.getArticleTitle();
        String name_of_folder = "Learning programming";
        ArticlePageObject.addArticleToMyListToNewFolder(name_of_folder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.openFolderByName(name_of_folder);
        MyListsPageObject.swipeByArticleToDelete(article_title);

    }

    @Test
    public void testAmountOfNotEmptySearch()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        String search_line = "Linkin Park Discography";
        SearchPageObject.typeSearchLine(search_line);
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();

        assertTrue(
                "We found too few results",
                amount_of_search_results > 0
                );
    }

    @Test
    public void testAmountOfEmptySearch()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        String search_line = "zxcvasdfqwre";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    public void testChangeScreenOrientationOnSearchResults()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        String title_before_rotation = ArticlePageObject.getArticleTitle();
        this.rotateScreenLandscape();
        String title_after_rotation = ArticlePageObject.getArticleTitle();

        assertEquals(
                "Article title have been changed after rotation",
                title_before_rotation,
                title_after_rotation
        );

        this.rotateScreenPortrait();

        String title_after_second_rotation = ArticlePageObject.getArticleTitle();

        assertEquals(
                "Article title have been changed after rotation",
                title_before_rotation,
                title_after_second_rotation
        );


    }

    @Test
    public void testCheckSearchArticleInBackground()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
        this.backgroundApp(2);
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testTwoArticlesSaveBothAndDeleteOne()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        String search_line1 = "Java";
        SearchPageObject.typeSearchLine(search_line1);
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();
        String first_article_name = ArticlePageObject.getArticleTitle();
        String name_of_folder = "Test folder";
        ArticlePageObject.addArticleToMyListToNewFolder(name_of_folder);
        ArticlePageObject.closeArticle();

        SearchPageObject.initSearchInput();
        String search_line2 = "JavaScript";
        SearchPageObject.typeSearchLine(search_line2);
        SearchPageObject.clickByArticleWithSubstring("Programming language");
        String second_article_name = ArticlePageObject.getArticleTitle();
        ArticlePageObject.addArticleToMyListToExistingFolder(name_of_folder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyLists();
        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.openFolderByName(name_of_folder);
        MyListsPageObject.swipeByArticleToDelete(first_article_name);

        SearchPageObject.clickByArticleWithSubstring(search_line2);
        String actual_result = ArticlePageObject.getArticleTitle();
        assertEquals(
                "Article title does not match the saved article's one",
                second_article_name,
                actual_result
        );

    }


    @Test
    public void testArticleTitleAvailability()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        String search_line1 = "Java";
        SearchPageObject.typeSearchLine(search_line1);
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForArticleHeaderToAppear();
        ArticlePageObject.assertTitlePresence();
    }

}
