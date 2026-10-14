# Proj2: Group21 - LinkedChainCollection

## Build, Run, and Test:

    ./gradlew build
    ./gradlew test
    ./gradlew run

## Reflection:

    What changed from your specification? Classes, method signatures, where validation lives, test cases — what did the spec say, what did you build instead, and why?
        - There were a few extra method signatures that were needed. Driver has two methods added, removeCollectible and displayCollection. 
        - Hover also changed from holding an integer with the index of a collectible as an int to holding the reference for said collectible, since we are working with a LinkedChain and not an array. Also, more constructors were added for the collectibles. What changed in the LinkedChain? Anything you added to or modified in the provided class and why your application needed it, or that you used it unchanged.
        - In the LinkedChain, many return types needed to be changed to Object instead of T, and an additional constructor was added for making a collectible from a prexisting collection. 
    What happened to your unknowns? Of the things you listed in your scope as not knowing how to do, which did you figure out, and which are still open?
        - We have settled on three collectibles, ActionFigure, Stamp, and Trading card. We stil are not certain how to make a front-end GUI (and whether we will even do this stretch goal). 
    What doesn't work yet? MVP features, bad-input cases, or tests that are missing or failing in this prototype.
        - Currently, the collection display is not consistent across all collectible types. We have also not implemented any stretch goals yet. Otherwise this prototype is near complete.

## Next steps — in your README.md, under a “Next Steps” heading, lay out what you will do next to extend this prototype toward the final. Your plan should address:

    What is left to finish the MVP? The remaining features and fixes, in the order you will do them.
        - The MVP will be finished once the collectibles display and are editable consistently. We will fix the editing inconsistencies first, and then the display. We will update our test files to reflect all of these changes. 
    Which stretch goals will you attempt? The ones from your scope you are committing to, and the ones you are dropping.
        - We will change Collection to be an implementor of Collectible, allowing a Collection object to exist as a sub-collection with a preexisting collection. Collections will be able to hold other Collections. 
        - We will add two more collectibles
        - We will be dropping the goal to make an external GUI for the program
    What new tests will you need? The methods and bad-input cases the added work must cover.
        - CollectionTest will need to be updated for the extra methods that allow Collection to function as a collectible
        - We will need new test files for each new Collectible type
        - We will potentially need to update the edit and display tests after we fix their existing issues. 
    Who does what? Which team member owns each remaining item.
        - Elijah: Create a new Collectible type and tests for that Collectible, helps with display and edit inconsistencies
        - Lukas A: Create a new Collectible type and tests for that Collectible
        - Lucas T: Adjust Collection to function as a Collectible 
        - Rin H: Adjust display and edit to consistently output amongst all collectible types

## Attribution — in your README.md, a table with one row per class and test file listing who worked on it and how (wrote revised, reviewed, tested, GenAI-assisted, …), plus a note on the way in which you used GenAI, if at all.

    Elijah O:
        Wrote: Collectible, TradingCard, TradingCardTest
        Revised: README
    Lukas A:
        Wrote (Gen-AI Assisted: helped with test case ideas): ActionFigure, ActionFigureTest, Stamp, StampTest
    Lucas T:
        Wrote: LinkedChain interface, Collection class, and CollectionTest
        Revised: ActionFigureTest, StampTest, TradingCardTest, Input, and InputTest
    Rin H:
        Wrote: README, Driver class, Input class, and InputTest
        Revised: ActionFigure, Stamp
        Reviewed: ActionFigureTest, StampTest, TradingCardTest
