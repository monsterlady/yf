## Technical design / Work in progress

Refer to the [library documentation](library-documentation.md) as an example on how to use Markdown and PlantUML for technical documentation.

## Rendering library

To allow students to get games up-and-running quickly, and to provide solutions for some aspects that are especially unintuitive when starting out with Android game programming, a small library and some example uses have been provided. They can be used as a base for your work. You're very much encouraged to make changes to the library to fit your needs! Or at least, make sure you have a basic understanding of what is happening.  

This document describes how to use this library.


### About this document

This document is written in Markdown using PlantUML for diagrams. To view and edit it from within Android Studio, install the 'Markdown Navigator' plugin.


### Features and scope

The provided library and examples demonstrate how to:

- Draw scaled, rotated and semi-transparent images.
- Animate fluently at 60 frames per second.
- Maintain game speed when the hardware is not capable of achieving the full frame rate. 
- Render on any screen resolution, without much effort.
- Sensibly organize your game logic into independent Entity instances.
- Handle touch events.

If you're creating a game that does not use fluent canvas animation, you're probably better of *not* starting out with this library. This may be the case when your game:
- only changes state as an immediate response to user touches, or
- doesn't use the canvas but only (or mostly) uses native Android Views like `TextView`, `ImageView` and `CheckBox`.


### Overview

The state of a game is represented by a `GameModel` object, that contains a list of objects extending `Entity`. Each `Entity` represents a visible entity or actor in the game. The `GameModel` is meant to be subclassed, to specify specifics for your game.

A `GameView` is an Android custom view. It collaborates with `GameModel` (and its `Entity`s) to render the graphical representation of the game state. `GameView`s offer methods for easily drawing scaled, rotated and semi-transparent bitmaps, and make it easy to work in a resolution independent way.


### Class diagram

This UML class diagram shows the use of the base library with a simple example game. Many details have been intentionally left out.

```plantuml

skinparam class {
    BackgroundColor<< lib >> white
}

class Constants{
    + coins
    + Price[]
    + resetCoins()
}

class DataWrapper{
    - instance
    
    + coins
    + DataWrapper()
    + getInstance()
    + setInstance()
    + getCoins()
    + setCoins()
    
}

class JsonHandler(){
    - context
    
    + JsonHandler()
    + loadConstants()
    + saveConstants()
}

class Save {
    - coins
    - prices
    - save
    
    + Save()
    + getCoins()
    + setSave()
    + getInstance()
    + setCoins()
    + setPrices()
    + getPrices()
}

class IntroActivity{
    - startGame
    - jsonHandler
    
    + onCreate()
    + openActivity()
}

class Typewriter{
    - mText
    - mIndex
    - mHandler
    - characterAdder
    
    + Typewriter()
    + animateText()
    + setCharacterDelay()
}

class Price{
    - maxTimesUpgrade
    - startPrice
    - currentPrice
    - risingPrices
    - multiply
    - decrease_multiply
    - priceCounter
    
    + Price()
    + init()
    + getNewPrice()
    + getCurrentPrice()
    + getPriceCounter()
}

class Vector{
    + x
    + y
    
    + Vector()
    + ifNumbersAreZero()
}

class MusicPlayer{
    - mediaPlayer
    
    + MusicPlayer()
    + playMusic()
    + pauseMusic()
    + stop()
    + stopMusic()
}

class SoundEffects{
    - soundPool
    - chopSounds
    
    + SoundEffects()
    + playChopSound()
    + initChopSounds()
    + playCoinSound()
    + initCoinSound()
}

class GlobalApplication{
    - applicationContext
    
    + onCreate()
    + getAppContext()
}

class Activity{
    - game
    - gameView
    - coinIndicator
    - buyView
    - jsonHandler
    - REMOVE_ME
    
    # onCreate()
    + setPrices()
    + setTextIndicator()
    + onClick()
    + onSaveInstanceState()
    # onResume()
    # onPause()
    
}

class Background{
    - bitmap
    - game
    
    + Background()
    + draw()
}

class BuyView{
    - buyNewCoin
    - buyNewAxe
    - buyChainsaw
    - buyFuel
    - game
    - prices
    - NUMBER_OF_UPDATES
    
    
    
    + BuyView()
    + onTouchEvent()
    + setGame()
    + transparent()
    + setPrices()
    - init()
    - proceedBuyNewCoin()
    - makeToast()
    + getPrices()
    - updateInfo()
    
    
}
class CoinElement{
    + WIDTH
    + HEIGHT
    - FRAMES_PER_SECOND
    + MIN_X_SPEED
    - MIN_HEIGHT
    - MAX_WIDTH
    - doAnimation
    - tickRate
    - gravity
    - MULTIPLY
    - DELETER
    - bouncer
    - deBouncer
    - airFriction
    - inversion
    - goRight
    - goLeft
    - position
    - direction
    
    + CoinElement()
    + tick()
    + draw()
    + setPosition()
    + getPosition()
    + setDirection()
    + setUpVarriables
    
}
class CoinGenerator{
    - game
    - soundEffects
    - NUMBER_OF_COINS
    - frameForCoin
    - tickRate
    - lastTimeTouched
    - PERIOD_BETWEEN_COLLECTING
    - coins
    
    + CoinGenerator()
    + tick()
    + handleTouch()
    + draw()
    - generateNumber()
    + setNUMBER_OF_COINS()
    + getNUMBER_OF_COINS()
}

class Game {
    - coinsEarned
    - gameActivity
    - background
    - treeGenerator
    - coinGenerator
    - lumberjack
    - treeChopped
    
    + Game()
    + start()
    + setTreeChopped()
    + setTreeChopped()
    + setGameActivity()
    + setCoinsEarned()
    + ifTreeChopped()
    + updateTextView()
    + getCoinsEarned()
    + addCoinToSpawn()
    + getGameActivity()
    + getWidth()
    + getHeight()
   
}

class MainActivity {
}



class Lumberjack{
    - context
    - soundEffects
    - width
    - height
    - TIME_TO_DO_ALL_ANIMATION
    - tickRate
    - lastTouched
    - touched
    - touchesToDestroy
    - leftTouchesToDestroy
    - bitmap
    - game
    - frame
    
    + Lumberjack()
    + tick()
    + handleTouch()
    + draw()
    + setContext()
    
}

class TreeElement{
    + bitmap
    + position
    - width
    - height
    
    + TreeElement()
    + draw()
    
}

class TreeGenerator{
    - logs
    - TREE_X_AXIS
    - LOGS_AMOUNT
    - game
    
    + TreeGenerator()
    + tick()
    + moveLogDown()
    + draw()
    
}


class Entity<<lib>> {
    + draw(GameView)
    + tick()
    + handleTouch(Touch)
    + getLayer(): int
}

class GameModel<<lib>> {
    - actualWidth
    - actualHeight
    + getWidth()
    + getHeight()
    + start()
    + addEntityEntity)
    + removeEntity(Entity)
    + getEntities(X.class): List<X>
}

class GameView<<lib>> {
    + setGame(GameModel)
    + setPaused(boolean)

    + drawBitmap(...)
    + getCanvas(): Canvas
}


GameModel o-> "*" Entity

GameView --> GameModel

Game -|> GameModel
Game *-- Lumberjack
Coins *-- Lumberjack
TreeGenerator --* TreeElement
Game *-- BuyView
CoinGenerator --* CoinElement
IntroActivity --* Typewriter
TreeGenerator --* TreeElement
MusicPlayer --* SoundEffects
DataWrapper --* Constants
Save --* Constants
CoinElement --|> Vector
CoinGenerator --|> Vector
CoinGenerator --|> SoundEffects
CoinGenerator --|> GameModel
CoinGenerator --|> Save
CoinGenerator --|> JsonHandler
CoinGenerator --|> GlobalApplication
TreeElement --|> Vector
BuyView --|> Save
BuyView --|> Price
Activity --|> GameView
Activity --|> Vector
Background --|> GameView
Constants --|> Price
Save --|> Price







Entity <|-- Lumberjack
Entity <|-- Background
Entity <|-- TreeElement
Entity <|-- CoinsElement








MainActivity --> Game
MainActivity -> GameView


class android.View {
    + onDraw()
}
class android.Activity {
    + onCreate()
    + onResume()
    + onPause()
}
android.Activity <|-- MainActivity 
android.View <|-- GameView

```


### Class reference

#### Entity

Although Model View Control (MVC) is often a good idea, it's use is not really convenient for games. In this library, the central concept is the `Entity`, which you can think of as a thing that is visible in the game, or changing the state of the game. Examples: the hero, a cloud, a bullet, an enemy cruiser, and the planet shattering megaboss. But HUD elements (like your health, inventory or buttons) and non-visible elements (like a victory checker, a monster spawner or a collision checker) may also be game objects.

You can create a new kind of game object by extending from `Entity`. `Entity`s need to be added to the `GameModel` by calling `GameMode.addEntity(entity)`. Similarly, entities can be removed using `GameModel.removeEntity(entity)`.

There are four methods on `Entity` that you may want to override:
- `draw(GameView)`. Called each time the screen is refreshed (usually up to 60 times per second). If the entity wants to display something, it will need to do that here onto the given GameView (discussed later).
- `tick()`. Called on every game object 180 times per second (configurable by overriding `GameLoop.ticksPerSecond()`). This method is to update the game state one step. So a bullet may want to move forward and check if it is hitting something. The reason the tick rate is (by default) higher than the maximum frame rate, is that this makes things like collision detection more accurate. Also, when the full frame rate cannot be met, smaller tick steps make for a more fluent experience.
- `getLayer()`. Returns the layer this entity is on. The draw and tick methods are usually called in the order the entities were created. In case this is not desirable, you can put game objects on a different layer. Let's say you want to create a new cloud, but that cloud should be drawn underneath (thus before) the hero, you can give clouds a lower layer number. The default layer number is 0, so the clouds could be put on layer -1.
- `handleTouch(Game.Touch, MotionEvent)`. Used to act on touch events such as `ACTION_UP`, `ACTION_DOWN` or `ACTION_MOVE`. The `Touch` object provides coordinates (translated to the virtual screen), movement, time, etc.


#### GameModel

The `GameModel` class is meant to be subclassed by your own class that contains specifics about your game.

A `GameModel` object represents the state of a single game, mostly by way of a list of `Entity`s.

`GameModel`s are `serializable`, meaning the entire game state can easily be converted to and from a `String`. This can be used in the Android Activity to, for instance, resume a game after it has been moved to the background and stopped by the operating system. 

Methods on `Game` you may want to call:

- addEntity(Entity) will add an `Entity` to the game.
- removeEntity(Entity) will remove an `Entity` from the game.
- `getEntities(Class)` returns a list of `Entity`s that are of type `Class`. For instance:
  ```java
  List<Car> cars = Game.getObjects(Car.class);
  ````

Methods you may want to override:

- `getWidth()`, returning the width of the virtual screen you want to use. You could use a constant value for this, which makes reasoning about scale in your `draw()` methods really easy. However, if the aspect ratio of your `getWidth()` and `getHeight()` doesn't match that of the actual View, black bars will appear. To prevent this, you can base the virtual resolution on the actual resolution, available as protected variables `actualWidth` and `actualHeight`. These might change during the game though, for instance on orientation change.
- `getHeight()`, like `getWidth()`.
- `start()`, called once just before your game is first drawn. At this point, the width and height of the View are known, meaning you could meaningfully call `getWidth()` and `getHeight()` even if these are dependend upon the actual resolution. This makes the `start()` method a suitable point at which to instantiate your initial `Entity`s.
- `ticksPerSecond()`, which returns the number of `tick()` call that are performed per second. If your game doesn't use ticks (for instance when it only changes immediately based on touch events), you can set this to 0. It defaults to 180.


#### GameView

This Android custom View uses the canvas to display graphics. This is done by associating a `GameModel` with it, using `setGame(..)`. From that point on, the model will receive `tick()`s (by default 180 times per second) and will be redrawn up to 60 times per second.

Drawing happens by asking each `Entity`, in order, to `draw(GameView)` itself. The `Entity` can:
- Call `GameView.getCanvas()` to get access to the Android Canvas object, on which the usual methods like `drawRect(..)` are available.
- Use the helper method `GameView.drawBitmap(...)`, to easily copy a `Bitmap` image to a specific position on the canvas, while optionally rotating and scaling it, and adding translucency. A `Bitmap` can be created from an Android resource using the `GameView.getBitmapFromResource(int resourceId)` helper method. In case you only want to draw a part of the Bitmap (for instance when using sprite sheets), you can create a new Bitmap containing only the intended part using `Bitmap.createBitmap(..)`.

In both cases, the `GameView` will arrange for the `Canvas` to be in such a state that it is `GameModel.getWidth()` wide and `GameModel.getHeight()` high.


### Lumberjack

The main character of the game

This method displays our lumberjack with his axes.
It produces some animation of him chopping the tree and colecting money, he is our main character of the game.

### TreeElement
One of the objects in the game

This objects creates blocks of wood which are later used in TreeGenerator class, to produce an actual tree
for our players

### Background
Back drop for the levels

Creates an image for a background view and displays it

### Constants
Stores values such as coins and upgrades 

Has two parameters coins and prices[] which later on are used in the save class and then in jsonHelper 

### DataWrapper
Wraps data for updates using a json file

creates an instance of that class so it could only be used once as an object
has a method to return a coins amount.

### JsonHandler
saves data from datawrapper into a custom json file

Its a json helper which we are using to save our constants, and then read them from a saved json
file for purpose such as to display our upgrades and don't loose sensitive data

### Save
saves data for json to use

Save is a pretty simple class which saves all constants and then are used in json Handler class

### IntroActivity
runs a introduction to the game, with some visuals and text

- OnCreate() it creates a little black background with a help of another class it displays some text back story
and displays some background music with possibility to skip it.

### TypeWriter
types the text for the intro activity with sound effects 

TypeWritter is a helper class for Intro activity it is used to display a sound of taping the text and actual
type engine and animation 

### Price
used for upgrading items for a lumberjack

This class is one of the most difficult classes in the game, which is responsible for saving and actual doing
upgrades on the lumberjack class. We have some prices for the coins spawn, exes.

### Vector

Vector class is a helper class used in different classes for math problems

### MusicPlayer
Its a globally used class for sounds of the game

it has such methods in used as:
- stopMusic() for stopping music
- playMusic() to start playing music on different occasions 

### SoundEffects
this is a helper class for music player

This one contains all the sounds for certain events in our game, such as chooping a tree
collecting coins from the ground, type witting sound and a background music.

### GlobalApplication
this class is a helper class in need if somewhere we need to initiate application context which 
in our case is game class

### Activity 
In this class we manage our game start and using methods such as

- onPause() 
- onResume()
these methods are really common in the android developing, and this class declares and initiates a lot
of different classes and passes them on onto the main activity 

### BuyView
This class represents its own name

It creates a view for a in game store for upgrading characters in our case lumberjacks
items and how many coins he produces when cutting a tree. This class is really in a relationship with 
price class.

### CoinElement
This class represents coins in our game

In this class we create a coin as an object and use it when a tree is being chopped. This class is being
used in other class which is CoinGenerator.

### CoinGenerator
this class uses coinElement class to produce coins for cutting a tree down, coins are animated 
we used some difficult physics elements to display them separately.

### Game
This is most important class in our game and it represents itself.

In this class we initialize a lot different other objects. This class initialize the actual game and most
of its behaviours of the first look at the game.

### TreeGenerator
This class is being used to generate a tree 

Generation happens that we are using a 
- onDraw() method which takes wood block from a treeElement class and displays 5 of them which looks like
a tree. We implemented an animation of chopping the tree in here two so people would see after lumberjack hits the tree
5 times it falls down    
