/**
 * Created by mosk on 14.05.14.
 */
var x = 0, y = 0;
var ws;
var inGame = false;

var characters;
var main_hero_id;


$(document).ready(function(){
    ws	= new WebSocket("ws://localhost:8887");
    ws.onopen = function() {
        console.log("Connected");
        ws.send("User connected");
        $("#command").removeAttr("disabled");
        $("#sendButton").removeAttr("disabled");
        $("#createSingleGameSession").removeAttr("disabled");
        //$(this).attr("disabled", "true");

    };
    ws.onmessage = function(evt) {
        var message = evt.data;
        //console.log(message);
        process_message(message);
    };
    ws.onclose = function() {
    };


    $("#sendButton").on("click",  function(e) {
        var command =  $("#command").val();
        ws.send(command);
        console.log("Send:" + command);
    });
    $("#createSingleGameSession").on("click", function(e){
        ws.send("{\"commandType\": \"system\", \"action\": \"createSingleGameSession\"}");
        $(this).attr("disabled", "true");
        inGame = true;
    });

});
function process_message(message){
    //console.log(message);
    try{
        data = jQuery.parseJSON(message);
        switch(data.action){
            case "applyGameSessionData":
                console.log(message);

                characters = data.characters;
                main_hero_id = data.userCharacterId;
                console.log($.toJSON(characters));
                //locationData goes here
                break;
            case "updateGameSessionData":
                var charactersChanges = data.characters;
                for(var i = 0; charactersChanges[i]; i++){
                    //if(charactersChanges[i].x)
                    update_character(charactersChanges[i].id, charactersChanges[i]);
                }
                break;
        }
    }
    catch (e){
        console.log("something with parser:");
        console.log(message);
        console.log(e.message);
    }

}

function update_character(id, characterChange){
    var character_for_update = null;
    for(var i = 0; characters[i]; i++)
        if(characters[i].id == id){
            character_for_update = characters[i];
            break;
        }

    if(character_for_update == null){
        //todo something here
        console.log("i do nothing, whryyyyyyyyy...");
    }
    else{
        console.log("I update character");
        if(characterChange.x) character_for_update.x = characterChange.x;
        if(characterChange.y) character_for_update.y = characterChange.y;

        if(characterChange.direction) character_for_update.direction = characterChange.direction;

        if(characterChange.healthPoints) character_for_update.healthPoints = characterChange.healthPoints;
        if(characterChange.manaPoints) character_for_update.manaPoints = characterChange.manaPoints;
        if(characterChange.staminaPoints) character_for_update.staminaPoints = characterChange.staminaPoints;

        if(characterChange.maxHealthPoints) character_for_update.maxHealthPoints = characterChange.maxHealthPoints;
        if(characterChange.maxManaPoints) character_for_update.maxManaPoints = characterChange.maxManaPoints;
        if(characterChange.maxStaminaPoints) character_for_update.maxStaminaPoints = characterChange.maxStaminaPoints;

        if(characterChange.moving) character_for_update.moving = characterChange.moving;
        if(characterChange.weapontAttacking) character_for_update.weapontAttacking = characterChange.weapontAttacking;
        if(characterChange.magicAttacking) character_for_update.magicAttacking = characterChange.magicAttacking;
    }
}

var stage = new PIXI.Stage(0x66FF99);

// create a renderer instance.
var renderer = PIXI.autoDetectRenderer(640, 480);

// add the renderer view element to the DOM
//document.body.appendChild(renderer.view);
document.getElementById("game_section").appendChild(renderer.view);

kd.UP.press(pressedUp);
kd.DOWN.press(pressedDown);
kd.LEFT.press(pressedLeft);
kd.RIGHT.press(pressedRight);

kd.UP.up(releasedUp);
kd.DOWN.up(releasedDown);
kd.LEFT.up(releasedLeft);
kd.RIGHT.up(releasedRight);


requestAnimFrame( animate );
var g = new PIXI.Graphics();

g.drawCircle(x,y,15);
stage.addChild(g);
function animate() {

    requestAnimFrame( animate );
    //kd.tick();
    // render the stage
    g.clear();
    g.beginFill(0xFFCC00, 1);
    if(characters) {
        //console.log($.toJSON(characters));
        for (var i = 0; characters[i]; i++)
            g.drawCircle(characters[i].x, characters[i].y, 15);
    }
    renderer.render(stage);
}
function pressedUp(){
    if(inGame)ws.send("{\"commandType\": \"hero\", \"action\":\"pressedUp\"}");
}
function pressedDown(){
    if(inGame)ws.send("{\"commandType\": \"hero\", \"action\":\"pressedDown\"}");
}
function pressedLeft(){
    if(inGame)ws.send("{\"commandType\": \"hero\", \"action\":\"pressedLeft\"}");
}
function pressedRight(){
    if(inGame)ws.send("{\"commandType\": \"hero\", \"action\":\"pressedRight\"}");
}

function releasedUp(){
    if(inGame)ws.send("{\"commandType\": \"hero\", \"action\":\"releasedUp\"}");
}
function releasedDown(){
    if(inGame)ws.send("{\"commandType\": \"hero\", \"action\":\"releasedDown\"}");
}
function releasedLeft(){
    if(inGame)ws.send("{\"commandType\": \"hero\", \"action\":\"releasedLeft\"}");
}
function releasedRight(){
    if(inGame)ws.send("{\"commandType\": \"hero\", \"action\":\"releasedRight\"}");
}
