package com.ruse.donation;

import com.ruse.model.Item;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.entity.impl.player.Player;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;


/**
 * Using this class:
 * To call this class, it's best to make a new thread. You can do it below like so:
 * new Thread(new Donation(player)).start();
 */
public class Store implements Runnable {

    public static final String HOST = "198.12.12.226"; // website ip address
    public static final String USER = "forgott3_store_user";
    public static final String PASS = "=^UtDVWyxGji";
    public static final String DATABASE = "forgott3_store";

    private Player player;
    private Connection conn;
    private Statement stmt;

    /**
     * The constructor
     * @param player
     */
    public Store(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        try {
            if (!connect(HOST, DATABASE, USER, PASS)) {
                return;
            }

            System.out.println("Successfully connected!");
            String name = player.getUsername().replace("_", " ");
            System.out.println("Getting result set for player: "+name);
            ResultSet rs = executeQuery("SELECT * FROM payments WHERE player_name='"+name+"' AND status='Completed' AND claimed=0");

            List<StoreItem> purchases = new LinkedList<>();

            while (rs.next()) {
                int productId = rs.getInt("item_number");
                int quantity = rs.getInt("quantity");
                int paymentId = rs.getInt("id");
                String itemName = rs.getString("item_name");
                int paidAmount = (int) rs.getDouble("paid");
                String email = rs.getString("buyer");

                StoreItem p = new StoreItem(productId, quantity);
                purchases.add(p);
                player.incrementAmountDonated(paidAmount); // increments player donated amount
                String dmessage = "`"+player.getUsername()+"` has just made a purchase of $"+paidAmount+"\n"+
                        "The payment ID was: "+paymentId+".\n"+"The purchase details were:```\n"+
                        "Product ID: "+productId+"\n"+
                        "Quantity: "+quantity+"\n"+
                        "Product Name: "+itemName+"\n"+
                        "Buyer email: "+email+"\n"+
                        "```";


                rs.updateInt("claimed", 1); // do not delete otherwise they can reclaim!
                rs.updateRow();
            }

            for (StoreItem purchase : purchases) {
                final StoreItems storeItems = StoreItems.getStoreItems().get(purchase.productId);
                if (storeItems != null) {
                    for (Item item : storeItems.getItems()) {
                        World.sendMessage("<col=FF0000><shad=0>[Donation] " +
                                        Misc.optimizeText(player.getUsername().toLowerCase(Locale.ROOT)) + " has purchased  x" +
                                        purchase.quantity + " " + ItemDefinition.forId(item.getId()).getName() + "!",
                                "<col=FF0000><shad=0>Thank you for donating! ::store if you want to support the server!");
                        Item toGive = new Item(item.getId(), item.getAmount() * purchase.quantity);
                        player.getBank(0).add(toGive);
                    }
                } else {
                    System.out.println("Invalid product code found: " + purchase.productId);
                    conn.rollback();
                    return;
                }
            }

            destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param host the host ip address or url
     * @param database the name of the database
     * @param user the user attached to the database
     * @param pass the users password
     * @return true if connected
     */
    public boolean connect(String host, String database, String user, String pass) {
        try {
            this.conn = DriverManager.getConnection("jdbc:mysql://"+host+":3306/"+database, user, pass);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failing connecting to database!");
            return false;
        }
    }

    /**
     * Disconnects from the MySQL server and destroy the connection
     * and statement instances
     */
    public void destroy() {
        try {
            conn.close();
            conn = null;
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Executes an update query on the database
     * @param query
     * @see {@link Statement#executeUpdate}
     */
    public int executeUpdate(String query) {
        try {
            this.stmt = this.conn.createStatement(1005, 1008);
            int results = stmt.executeUpdate(query);
            return results;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    /**
     * Executres a query on the database
     * @param query
     * @see {@link Statement#executeQuery(String)}
     * @return the results, never null
     */
    public ResultSet executeQuery(String query) {
        try {
            this.stmt = this.conn.createStatement(1005, 1008);
            ResultSet results = stmt.executeQuery(query);
            return results;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
