package org.necrotic.client.cache.media.particles;

import java.util.*;

public class ParticleAttachment {

	private static final Map<Integer, int[][]> attachments = new HashMap<>();

	public static int[][] getAttachments(int model) {
        return attachments.get(model);
    }

	static {

		//BEAST BOW
		attachments.put(101322, new int[][] { { 260, 0 }, { 207, 0 }, { 606, 0 }, { 400, 0 } });
		//BEAST BOW (X)
		attachments.put(101326, new int[][] { { 260, 1 }, { 207, 1 }, { 606, 1 }, { 400, 1 } });
        //BEAST BLADE
		attachments.put(101324, new int[][] {  { 1930, 2 }, { 2075, 2 }, { 324, 2 }, { 324, 2 }, { 327, 2 } });
		//BEAST BLADE (X)
		attachments.put(101325, new int[][] {  { 27, 3 }, { 26, 3 }, { 25, 3 }, { 14, 3 }, { 180, 3 }, { 101, 3 }, { 29, 3 } });
		//BEAST STAFF
		attachments.put(101323, new int[][] {  { 27, 4 }, { 26, 4 }, { 25, 4 }, { 14, 4 }, { 180, 4 }, { 101, 4 }, { 29, 4 } });
		//BEAST STAFF (X)
		attachments.put(101327, new int[][] {  { 27, 5 }, { 26, 5 }, { 25, 5 }, { 14, 5 }, { 180, 5 }, { 101, 5 }, { 29, 5 } });
		//FORGOTTEN WINGS
		attachments.put(100997, new int[][] { {247, 15 }, { 250, 15 }, { 208, 15 }, { 209, 15 },  {429, 15 }, {430, 15 }, { 14, 15 }, { 36, 15 } });
		//FORGOTTEN CAPE
		attachments.put(101121, new int[][] { {303, 15 }, { 302, 15 }, { 301, 15 }, { 300, 15 },  {299, 15 }, {298, 15 }, { 297, 15 }, { 295, 15 } });
		//WIZARD CAPE
		attachments.put(101122, new int[][] { {303, 8 }, { 302, 8 }, { 301, 8 }, { 300, 8 },  {299, 8 }, {298, 8 }, { 297, 8 }, { 295, 8 } });
		//BERSERKER CAPE
		attachments.put(101120, new int[][] { {303, 9 }, { 302, 9 }, { 301, 9 }, { 300, 9 },  {299, 9 }, {298, 9 }, { 297, 9 }, { 295, 9 } });
		//MARKSMAN CAPE
		attachments.put(101124, new int[][] { {303, 10 }, { 302, 10 }, { 301, 10 }, { 300, 10 },  {299, 10 }, {298, 10 }, { 297, 10 }, { 295, 10 } });
		// EXP BOW
		attachments.put(100715, new int[][] { {207, 11 }, { 235, 11 }, { 311, 11 }, { 168, 11 },  {256, 11 }, {288, 11 } });
        //MARKSMAN WINGS
		attachments.put(100998, new int[][] { {247, 10 }, { 250, 10 }, { 208, 10 }, { 209, 10 },  {429, 10 }, {430, 10 }, { 14, 10 }, { 36, 10 } });
        //BERZERKER WINGS
		attachments.put(100999, new int[][] { {247, 9 }, { 250, 9 }, { 208, 9 }, { 209, 9 },  {429, 9 }, {430, 9 }, { 14, 9 }, { 36, 9 } });
        //WIZARD WINGS
		attachments.put(101001, new int[][] { {247, 11 }, { 250, 11 }, { 208, 11 }, { 209, 11 },  {429, 11 }, {430, 11 }, { 14, 11 }, { 36, 11 } });
		// exclusive
		attachments.put(16052, new int[][] { {303, 6 }, { 302, 14 }, { 301, 12 }, { 300, 10 },  {299, 13 }, {298, 8 }, { 297, 15 }, { 295, 16 } });
		// Bussy Body
		attachments.put(16054, new int[][] { {720, 17 }, { 860, 17 }});
		// Bussy Legs
		//attachments.put(16055, new int[][] { {530, 17 }, { 613, 17 }});
		attachments.put(16059, new int[][] { {204, 17 }, { 205, 17 }, { 483, 17 }, { 484, 17 } });
		//white f cape
		attachments.put(16063, new int[][] { {303, 12 }, { 302, 12 }, { 301, 12 }, { 300, 12 },  {299, 12 }, {298, 12 }, { 297, 12 }, { 295, 12 } });

		attachments.put(16072, new int[][] { {247, 12 }, { 250, 12 }, { 208, 12 }, { 209, 12 },  {429, 12 }, {430, 12 }, { 14, 12 }, { 36, 12 } });



		attachments.put(16058, new int[][] { {303, 17 }, { 302, 17 }, { 301, 17 }, { 300, 17 },  {299, 17 }, {298, 17 }, { 297, 17 }, { 295, 17 } });

	}
}
