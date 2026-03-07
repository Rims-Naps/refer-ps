package com.ruse.world.content;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.GameObject;
import com.ruse.model.GroundItem;
import com.ruse.model.Item;
import com.ruse.model.Position;
import com.ruse.world.World;
import com.ruse.world.clip.region.RegionClipping;
import com.ruse.world.entity.impl.GroundItemManager;
import com.ruse.world.entity.impl.player.Player;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Handles customly spawned objects (mostly global but also privately for
 * players)
 *
 * @author Gabriel Hannason
 */
public class CustomObjects {

    private static final int DISTANCE_SPAWN = 70; // Spawn if within 70 squares of distance
    private static final CopyOnWriteArrayList<GameObject> CUSTOM_OBJECTS = new CopyOnWriteArrayList<>();
    private static final int[] HOME_OBJECTS = {4764, 4772};

    public static void init() {
        for (int i = 0; i < CUSTOM_OBJECTS_SPAWNS.length; i++) {
            int id = CUSTOM_OBJECTS_SPAWNS[i][0];
            int x = CUSTOM_OBJECTS_SPAWNS[i][1];
            int y = CUSTOM_OBJECTS_SPAWNS[i][2];
            int z = CUSTOM_OBJECTS_SPAWNS[i][3];
            int face = CUSTOM_OBJECTS_SPAWNS[i][4];
            GameObject object = new GameObject(id, new Position(x, y, z));
            object.setFace(face);
            CUSTOM_OBJECTS.add(object);
            World.register(object);
        }
        for (int i = 0; i < CLIENT_OBJECTS.length; i++) {
            int id = CLIENT_OBJECTS[i][0];
            int x = CLIENT_OBJECTS[i][1];
            int y = CLIENT_OBJECTS[i][2];
            int z = CLIENT_OBJECTS[i][3];
            int face = CLIENT_OBJECTS[i][4];
            GameObject object = new GameObject(id, new Position(x, y, z));
            object.setFace(face);
            RegionClipping.addObject(object);
        }
    }

    private static void handleList(GameObject object, String handleType) {
        switch (handleType.toUpperCase()) {
            case "DELETE":
                for (GameObject objects : CUSTOM_OBJECTS) {
                    if (objects.getId() == object.getId() && object.getPosition().equals(objects.getPosition())) {
                        CUSTOM_OBJECTS.remove(objects);
                    }
                }
                break;
            case "ADD":
                if (!CUSTOM_OBJECTS.contains(object)) {
                    CUSTOM_OBJECTS.add(object);
                }
                break;
            case "EMPTY":
                CUSTOM_OBJECTS.clear();
                break;
        }
    }

    public static void spawnObject(Player p, GameObject object) {
        if (object.getId() != -1) {
            p.getPacketSender().sendObject(object);
            if (!RegionClipping.objectExists(object)) {
                RegionClipping.addObject(object);
            }
        } else {
            deleteObject(p, object);
        }
    }

    public static void deleteObject(Player p, GameObject object) {
        p.getPacketSender().sendObjectRemoval(object);
        if (RegionClipping.objectExists(object)) {
            RegionClipping.removeObject(object);
        }
    }

    public static void deleteGlobalObject(GameObject object) {
        handleList(object, "delete");
        World.deregister(object);
    }

    public static void spawnGlobalObject(GameObject object) {
        handleList(object, "add");
        World.register(object);
    }

    public static void spawnGlobalObjectWithinDistance(GameObject object) {
        for (Player player : World.getPlayers()) {
            if (player == null)
                continue;
            if (object.getPosition().isWithinDistance(player.getPosition(), DISTANCE_SPAWN)) {
                spawnObject(player, object);
            }
        }
    }

    public static void deleteGlobalObjectWithinDistance(GameObject object) {
        for (Player player : World.getPlayers()) {
            if (player == null)
                continue;
            if (object.getPosition().isWithinDistance(player.getPosition(), DISTANCE_SPAWN)) {
                deleteObject(player, object);
            }
        }
    }

    public static boolean objectExists(Position pos) {
        return getGameObject(pos) != null;
    }

    public static GameObject getGameObject(Position pos) {
        for (GameObject objects : CUSTOM_OBJECTS) {
            if (objects != null && objects.getPosition().equals(pos)) {
                return objects;
            }
        }
        return null;
    }

    public static void handleRegionChange(Player p) {
        for (GameObject object : CUSTOM_OBJECTS) {
            if (object == null)
                continue;
            if (object.getPosition().isWithinDistance(p.getPosition(), DISTANCE_SPAWN)) {
                spawnObject(p, object);
            }
        }
    }

    public static void objectRespawnTask(Player p, GameObject tempObject, GameObject mainObject,
                                         int cycles) {
        deleteObject(p, mainObject);
        spawnObject(p, tempObject);
        TaskManager.submit(new Task(cycles) {
            @Override
            public void execute() {
                deleteObject(p, tempObject);
                spawnObject(p, mainObject);
                this.stop();
            }
        });
    }

    public static void globalObjectRespawnTask(GameObject tempObject, GameObject mainObject,
                                               int cycles) {
        deleteGlobalObject(mainObject);
        spawnGlobalObject(tempObject);
        TaskManager.submit(new Task(cycles) {
            @Override
            public void execute() {
                deleteGlobalObject(tempObject);
                spawnGlobalObject(mainObject);
                this.stop();
            }
        });
    }

    public static void globalObjectRemovalTask(GameObject object, int cycles) {
        spawnGlobalObject(object);
        TaskManager.submit(new Task(cycles) {
            @Override
            public void execute() {
                deleteGlobalObject(object);
                this.stop();
            }
        });
    }

    public static void globalFiremakingTask(GameObject fire, Player player, int cycles) {
        spawnGlobalObject(fire);
        TaskManager.submit(new Task(cycles) {
            @Override
            public void execute() {
                deleteGlobalObject(fire);
                if (player.getInteractingObject() != null && player.getInteractingObject().getId() == 2732) {
                    player.setInteractingObject(null);
                }
                this.stop();
            }

            @Override
            public void stop() {
                setEventRunning(false);
                GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(592), fire.getPosition(), player.getUsername(), false, 150, true, 100));
            }
        });
    }

    /**
     * Contains
     *
     * @param ObjectId - The object ID to spawn
     * @param absX     - The X position of the object to spawn
     * @param absY     - The Y position of the object to spawn
     * @param Z        - The Z position of the object to spawn
     * @param face     - The position the object will face
     */

    // Only adds clips to these objects, they are spawned clientsided
    // NOTE: You must add to the client's customobjects array to make them spawn,
    // this is just clipping!
    private static final int[][] CLIENT_OBJECTS = {

//            {-1, 3177, 3549, 0, 1},
//
//            {1317, 3176, 3548, 0, 2},


            {42910, 3166, 3506, 0, 1},

            {17953, 3163, 3527, 0, 1},

            {18835, 2008, 4763, 0, 0},

            //{4408, 2008, 4766, 4, 0},
            {4408, 2008, 4766, 0, 0},


            {1093, 3407, 5279, 0, 3},
            {1093, 3408, 5279, 0, 3},
            {1093, 3409, 5279, 0, 3},

            {25123, 3424, 5296, 0, 0},
            {25123, 3424, 5295, 0, 0},
            {25123, 3424, 5294, 0, 0},

            {5358, 3439, 5280, 0, 1},
            {5358, 3440, 5280, 0, 1},
            {5358, 3441, 5280, 0, 1},


            {52768, 2661, 3875, 0, 2},


            {7352, 2892, 3549, 0, 1},

            {27306, 2642, 3878, 0, 0},


            //TUTORIAL
            //DAMAGE ROCK
            {7591, 2321, 3427, 0, 0},
            //{7591, 2969, 3877, 0, 0},

            //DROPRATE ROCK
            {38662, 2332, 3438, 0, 0},
           // {38662, 2969, 3877, 0, 0},

            //CRIT ROCK
            {38661, 2327, 3423, 0, 0},
           // {38661, 2969, 3877, 0, 0},

            //DAMAGE ROCK
            {7591, 2587, 4141, 0, 0},
            //DROPRATE ROCK
            {38662, 2576, 4131, 0, 0},
            //CRIT ROCK
            {38661, 2596, 4122, 0, 0},


            //FRENZY SHIT
            {16090, 2513, 2775, 0, 2},
            {6774, 2540, 2796, 0, 1},
            {6774, 2540, 2793, 0, 1},

            {22942, 2969, 3877, 0, 0},

            {36000, 2326, 3426, 0, 1},


            {29943, 2985, 3235, 2, 2},//MAGIC CHEST



            //DZONE SALT ROCKS

            //DROP RATE
            {7591, 2760, 3539, 0, 0},
            {7591, 2632, 3539, 0, 0},
            {7591, 2504, 3539, 0, 0},
            {7591, 2376, 3539, 0, 0},

            //CRITICAL
            {38661, 2762, 3541, 0, 0},
            {38661, 2634, 3541, 0, 0},
            {38661, 2506, 3541, 0, 0},
            {38661, 2378, 3541, 0, 0},

            //DAMAGE
            {38662, 2762, 3537, 0, 0},
            {38662, 2634, 3537, 0, 0},
            {38662, 2506, 3537, 0, 0},
            {38662, 2378, 3537, 0, 0},


            //Fire Portal: 6282
            {6282, 2912, 3526, 0, 0},
            //Earth Portal: 38700
            {38700, 2936, 3550, 0, 0},
           // Water Portal: 42611
            {42611, 2912, 3573, 0, 0},


            //GAIA BARRIER 1
            {39714, 2202, 5332, 0, 0},

            {53, 2070, 5664, 0, 0},
            {53, 2071, 5664, 0, 0},

            {59, 2083, 5677, 0, 2},
            {59, 2083, 5678, 0, 0},


            {29577 , 2910, 2593, 0, 2},

            {38150 , 2850, 2529, 0, 0},
            {38150 , 2786, 2529, 0, 0},
            {38150 , 2786, 2657, 0, 0},


            //BANK CHEST HOME
            {2213, 3176, 3541, 0, 1},
            {42192, 2973, 3233, 2, 1},
            {26945, 3160, 3540, 0, 1},

            //LAGOON PORTAL
            {4408, 2521, 2913, 0, 0},
            {4408, 2521, 2913, 4, 0},


            {43667,2626,3795,0,3},




          /*  //UPGRADE ROCK
            {21432, 2799, 4046, 0, 0},
            {21432, 2756, 4047, 0, 0},
*/

            {40568,2790,4091,0,0},
            {40568,2781,4079,0,0},


            {41200, 2850, 2525, 0, 3},//RED COFFIN
            {41201, 2786, 2525, 0, 3},//BLUE COFFIN
            {41202, 2786, 2653, 0, 3},//GREEN COFFIN


            //tree
            {1345,2786,4092,0,0},
            {1345,2782,4091,0,0},
            {1345,2778,4090,0,0},
            {1345,2777,4080,0,0},
            {1345,2787,4081,0,0},

            //blue rock
            {49780,2802,4062,0,1},
            {49780,2801,4064,0,2},
            {49780,2800,4064,0,1},
            {49780,2798,4063,0,2},
            {49780,2806,4061,0,0},
            {49780,2807,4062,0,0},
            {49780,2811,4066,0,1},
            {49780,2810,4066,0,1},
            {49780,2809,4067,0,1},

            //purple rock
            {49806,2785,4048, 0, 0},
            {49806,2785,4047, 0, 0},
            {49806,2784,4045, 0, 0},
            {49806,2782,4045, 0, 0},
            {49806,2784,4040, 0, 0},
            {49806,2781,4040, 0, 0},
            {49806,2787,4036, 0, 0},
            {49806,2784,4036, 0, 0},
            {49806,2784,4034, 0, 0},


            /* Start of Woodcutting Area Fixes */
            {1315, 2558, 3869, 0, 0}, {1315, 2553, 3863, 0, 0}, {1315, 2551, 3860, 0, 0},
            {1315, 2551, 3871, 0, 0}, {1315, 2543, 3859, 0, 0}, {1315, 2536, 3866, 0, 0},
            /* End of Woodcutting Area Fixes */
            /* beanstalk */
            {-1, 2139, 5518, 3, 1}, {-1, 2139, 5520, 2, 1}, {-1, 2141, 5522, 2, 1},
            /* Fishing Guild */
            {10091, 2612, 3411, 0, 2},
            /* Varrock Smith */
            {-1, 3231, 3441, 0, 2}, {-1, 3230, 3441, 0, 2}, {2783, 3231, 3442, 0, 2}, {6189, 3231, 3440, 0, 2},
            /* Remmington random shit */
            {-1, 2978, 3239, 0, 1}, {-1, 2980, 3240, 0, 1}, {-1, 2979, 3241, 0, 1}, {-1, 2972, 3245, 0, 1},
            {-1, 2972, 3246, 0, 1},
            // woodcutting firemaking area
            {2732, 2539, 3891, 0, 1},
            // test fog
            {860, 2250, 3315, 0, 1}, {860, 2250, 3321, 0, 1}, {860, 2256, 3321, 0, 1}, {860, 2256, 3315, 0, 1},
            /* Removing Entrana Things {-1, x, y, 0, 1}, */
            {-1, 2857, 3350, 0, 1}, {-1, 2857, 3347, 0, 1}, {-1, 2853, 3355, 0, 1}, {-1, 2851, 3353, 0, 1},
            {-1, 2849, 3355, 0, 1}, {-1, 2849, 3354, 0, 1}, {-1, 2849, 3353, 0, 1}, {-1, 2853, 3344, 0, 1},
            {-1, 2853, 3342, 0, 1}, {-1, 2852, 3345, 0, 1}, {-1, 2852, 3344, 0, 1}, {-1, 2852, 3342, 0, 1},
            {-1, 2850, 3342, 0, 1}, {-1, 2849, 3343, 0, 1}, {-1, 2849, 3342, 0, 1}, {-1, 2848, 3350, 0, 1},
            {-1, 2846, 3350, 0, 1}, {-1, 2844, 3350, 0, 1}, {-1, 2842, 3350, 0, 1}, {-1, 2848, 3346, 0, 1},
            {-1, 2846, 3346, 0, 1}, {-1, 2844, 3346, 0, 1}, {-1, 2842, 3346, 0, 1}, {-1, 2847, 3345, 0, 1},
            {-1, 2845, 3345, 0, 1}, {-1, 2843, 3345, 0, 1}, {-1, 2841, 3345, 0, 1}, {-1, 2847, 3352, 0, 1},
            {-1, 2845, 3352, 0, 1}, {-1, 2843, 3352, 0, 1}, {-1, 2841, 3352, 0, 1},
            {-1, 3098, 3496, 0, 1}, {-1, 3095, 3498, 0, 1}, {-1, 3088, 3509, 0, 1}, {-1, 3095, 3499, 0, 1},
            {-1, 3085, 3506, 0, 1}, {-1, 3206, 3263, 0, 0}, {-1, 2794, 2773, 0, 0}, {2, 2692, 3712, 0, 3},
            {2, 2688, 3712, 0, 1}, {4875, 3671, 2972, 0, 0}, {4874, 3672, 2972, 0, 0}, {4876, 3673, 2972, 0, 0},
            {410, 3680, 2985, 0, 2}, {2273, 2384, 4719, 0, 2}, {11758, 3019, 9740, 0, 0},
            {4306, 3026, 9741, 0, 0}, {6189, 3022, 9742, 0, 1}, {75, 2914, 3452, 0, 2},
            {10091, 2352, 3703, 0, 2}, {11758, 3449, 3722, 0, 0}, {11758, 3450, 3722, 0, 0},
            {50547, 3445, 3717, 0, 3}, {-1, 3090, 3496, 0, 0}, {-1, 3090, 3494, 0, 0}, {-1, 3092, 3496, 0, 0},
            {-1, 3659, 3508, 0, 0}, {4053, 3660, 3508, 0, 0}, {4051, 3659, 3508, 0, 0}, {1, 3649, 3506, 0, 0},
            {1, 3650, 3506, 0, 0}, {1, 3651, 3506, 0, 0}, {1, 3652, 3506, 0, 0}, {8702, 3423, 2911, 0, 0},
            {47180, 3422, 2918, 0, 0}, {11356, 3418, 2917, 0, 1}, {-1, 2860, 9734, 0, 1},
            {-1, 2857, 9736, 0, 1}, {-1, 3671, 2981, 0, 2}, {-1, 3671, 2981, 0, 2}, {-1, 3672, 2981, 0, 2}, {-1, 3671, 2980, 0, 2},
            {-1, 3670, 2981, 0, 2}, {-1, 3671, 2982, 0, 2}, {-1, 3671, 2981, 0, 2},
            {-1, 3668, 2980, 0, 2}, {-1, 3667, 2979, 0, 2}, {-1, 3668, 2978, 0, 2}, {-1, 3669, 2979, 0, 2},
            {-1, 3665, 2983, 0, 2}, {-1, 3665, 2981, 0, 2}, {-1, 3665, 2979, 0, 2},
            {-1, 3668, 2983, 0, 2}, {-1, 3669, 2983, 0, 2}, {-1, 3668, 2977, 0, 2},
            {-1, 3668, 2977, 0, 2}, {-1, 3670, 2976, 0, 2}, {-1, 3667, 2977, 0, 2}, {-1, 3665, 2977, 0, 2},
            {-1, 3668, 2979, 0, 2}, {737, 2887, 4377, 0, 2}, {699, 2885, 4378, 0, 2},
            {-1, 3664, 2976, 0, 2}, {-1, 3664, 2977, 0, 2}, {-1, 3664, 2978, 0, 2}, {-1, 3664, 2979, 0, 2},
            {-1, 3664, 2980, 0, 2}, {-1, 3664, 2981, 0, 2}, {-1, 3664, 2982, 0, 2}, {-1, 3664, 2983, 0, 2},
            {-1, 3664, 2984, 0, 2}, {-1, 3683, 2982, 0, 2}, {-1, 3683, 2984, 0, 2},
            {-1, 3681, 2981, 0, 2}, {-1, 3681, 2982, 0, 2}, {-1, 3681, 2983, 0, 2},
            {-1, 3666, 2991, 0, 0}, {-1, 3666, 2992, 0, 0}, {2732, 2848, 3335, 0, 0},
            //MINING AREA
            {50193, 3037, 3996, 0, 0},
            {6150, 3037, 3992, 0, 0},

            {8469, 2537, 2771, 0, 0},

    };

    private static final int[][] CUSTOM_OBJECTS_SPAWNS = {

            {1093, 3407, 5279, 0, 3},
            {1093, 3408, 5279, 0, 3},
            {1093, 3409, 5279, 0, 3},

            //{4408, 2008, 4766, 4, 0},
            {4408, 2008, 4766, 0, 0},


            {25123, 3424, 5296, 0, 0},
            {25123, 3424, 5295, 0, 0},
            {25123, 3424, 5294, 0, 0},

            {5358, 3439, 5280, 0, 1},
            {5358, 3440, 5280, 0, 1},
            {5358, 3441, 5280, 0, 1},

            {52768, 2661, 3875, 0, 2},


            {27306, 2642, 3878, 0, 0},

            {7352, 2892, 3549, 0, 1},

            {40568,2790,4091,0,0},
            {40568,2781,4079,0,0},

            {41200, 2850, 2525, 0, 3},//RED COFFIN
            {41201, 2786, 2525, 0, 3},//BLUE COFFIN
            {41202, 2786, 2653, 0, 3},//GREEN COFFIN


            {38150 , 2850, 2529, 0, 0},
            {38150 , 2786, 2529, 0, 0},
            {38150 , 2786, 2657, 0, 0},

//            {-1, 3177, 3549, 0, 1},
//            {1317, 3176, 3548, 0, 2},


            //tree
            {1345,2786,4092,0,0},
            {1345,2782,4091,0,0},
            {1345,2778,4090,0,0},
            {1345,2777,4080,0,0},
            {1345,2787,4081,0,0},

            {43667,2626,3795,0,3},

            //{29577 , 2910, 2593, 0, 0},
            //blue rock
            {49780,2802,4062,0,1},
            {49780,2801,4064,0,2},
            {49780,2800,4064,0,1},
            {49780,2798,4063,0,2},
            {49780,2806,4061,0,0},
            {49780,2807,4062,0,0},
            {49780,2811,4066,0,1},
            {49780,2810,4066,0,1},
            {49780,2809,4067,0,1},

            //purple rock
            {49806,2785,4048, 0, 0},
            {49806,2785,4047, 0, 0},
            {49806,2784,4045, 0, 0},
            {49806,2782,4045, 0, 0},
            {49806,2784,4040, 0, 0},
            {49806,2781,4040, 0, 0},
            {49806,2787,4036, 0, 0},
            {49806,2784,4036, 0, 0},
            {49806,2784,4034, 0, 0},


            {36000, 2326, 3426, 0, 1},

            {42910, 3166, 3506, 0, 1},
            {17953, 3163, 3527, 0, 1},

            //LAGOON PORTAL
            {4408, 2521, 2913, 0, 0},
            {4408, 2521, 2913, 4, 0},


            //FRENZY SHIT
            {16090, 2513, 2775, 0, 2},
            {6774, 2540, 2796, 0, 1},
            {6774, 2540, 2793, 0, 1},

            {8469, 2537, 2771, 0, 0},


            //BANK CHEST HOME
            {2213, 3176, 3541, 0, 1},
            {42192, 2973, 3233, 2, 1},
            {26945, 3160, 3540, 0, 1},
            //TUTORIAL
            //DAMAGE ROCK
            {7591, 2321, 3427, 0, 0},
            //{7591, 2969, 3877, 0, 0},
            //DROPRATE ROCK
            {38662, 2332, 3438, 0, 0},
            // {38662, 2969, 3877, 0, 0},
            //CRIT ROCK
            {38661, 2327, 3423, 0, 0},
            // {38661, 2969, 3877, 0, 0},
            //TUTORIAL

            {29943, 2985, 3235, 2, 2},//MAGIC CHEST


            //DAMAGE ROCK
            {7591, 2587, 4141, 0, 0},
            //DROPRATE ROCK
            {38662, 2576, 4131, 0, 0},
            //CRIT ROCK
            {38661, 2596, 4122, 0, 0},

            //Fire Portal: 6282
            {6282, 2911, 3526, 0, 0},
            //Earth Portal: 38700
            {38700, 2936, 3549, 0, 1},
            // Water Portal: 42611
            {42611, 2911, 3573, 0, 0},


            //GAIA BARRIER 1
            {39714, 2202, 5332, 0, 0},


            //MINING AREA
            {50193, 3037, 3996, 0, 0},
            {6150, 3037, 3992, 0, 0},

    };
}
