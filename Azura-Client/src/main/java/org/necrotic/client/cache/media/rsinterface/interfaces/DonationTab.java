package org.necrotic.client.cache.media.rsinterface.interfaces;

import org.necrotic.ColorConstants;
import org.necrotic.client.Client;
import org.necrotic.client.cache.media.RSInterface;
import org.necrotic.client.util.DummyItem;
import org.necrotic.client.util.Milestone;

public class DonationTab extends RSInterface {
    private static final int STARTING_POINT = 28500;
    private static final int WIDTH = 183;
    private static final int HEIGHT = 255;

    static Object[][] information = {
        {"Server Campaign", 5701},
        {"Lifetime Campaign", 5701},
        {"Monthly Campaign", 5701}
    };
    static Milestone[] milestones1 = {
        new Milestone(500, "Spawns a Donation Boss", Milestone.MilestoneType.DESCRIPTION, 2000),
        new Milestone(1000, new DummyItem[]{
            new DummyItem(4151, 1),
            new DummyItem(4151, 2),
            new DummyItem(4151, 3)}, Milestone.MilestoneType.ITEMS, 2000),
        new Milestone(2000, new DummyItem[]{
            new DummyItem(4151, 1),
            new DummyItem(4151, 2),
            new DummyItem(4151, 3)}, Milestone.MilestoneType.ITEMS, 2000),
    };

    static Milestone[] milestones2 = {
        new Milestone(100, new DummyItem[]{
            new DummyItem(4151, 1),
            new DummyItem(4151, 2),
            new DummyItem(4151, 3)}, Milestone.MilestoneType.ITEMS, 2000),
        new Milestone(250, new DummyItem[]{
            new DummyItem(4151, 1),
            new DummyItem(4151, 2),
            new DummyItem(4151, 3)}, Milestone.MilestoneType.ITEMS, 2000),
        new Milestone(500, new DummyItem[]{
            new DummyItem(4151, 1),
            new DummyItem(4151, 2),
            new DummyItem(4151, 3)}, Milestone.MilestoneType.ITEMS, 2000),
        new Milestone(1000, new DummyItem[]{
            new DummyItem(4151, 1),
            new DummyItem(4151, 2),
            new DummyItem(4151, 3)}, Milestone.MilestoneType.ITEMS, 2000),
        new Milestone(2000, new DummyItem[]{
            new DummyItem(4151, 1),
            new DummyItem(4151, 2),
            new DummyItem(4151, 3)}, Milestone.MilestoneType.ITEMS, 2000),
    };

    static Milestone[] milestones3 = {
        new Milestone(250, new DummyItem[]{
            new DummyItem(4151, 2),
                new DummyItem(4151, 1),
            new DummyItem(4151, 3)}, Milestone.MilestoneType.ITEMS, 1000),
        new Milestone(500, new DummyItem[]{
            new DummyItem(4151, 1),
            new DummyItem(4151, 2),
            new DummyItem(4151, 3)}, Milestone.MilestoneType.ITEMS, 1000),
        new Milestone(1000, new DummyItem[]{
            new DummyItem(4151, 1),
            new DummyItem(4151, 2),
            new DummyItem(4151, 3)}, Milestone.MilestoneType.ITEMS, 1000),
    };


    public static void unpack() {
        server_campaign();
    }

    public static void server_campaign() {
        int localIds = STARTING_POINT + 100;
        RSInterface main = addInterface(localIds++);
        RSInterface donationComp = addInterface(localIds++);
        int childId = 0;
        int child2 = 0;
        main.totalChildren(3);
        donationComp.totalChildren(3);

        addProgressBarMilestones(localIds, 150, 150, 2500, (int) information[0][1], (int) information[0][1], 152, 27, milestones1);
        donationComp.child(child2++, localIds++, 11, 110);

        addProgressBarMilestones(localIds, 150, 150, 2500, (int) information[1][1], (int) information[1][1], 152, 27, milestones2);
        donationComp.child(child2++, localIds++, 11, 160);

        addProgressBarMilestones(localIds, 150, 150, 2500, (int) information[2][1], (int) information[2][1], 152, 27, milestones3);
        donationComp.child(child2, localIds++, 11, 210);

        addWindow(localIds, WIDTH - 1, HEIGHT, 2, false, false);
        main.child(childId++, localIds++, 4, 3);

        addSpriteLoader(localIds, 2498);
        main.child(childId++, localIds, 9, 8);

        main.child(childId, donationComp.id, 9, 8);
    }
}
