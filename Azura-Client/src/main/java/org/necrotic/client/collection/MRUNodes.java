package org.necrotic.client.collection;

import org.necrotic.client.Signlink;

public final class MRUNodes {

	private final NodeSub emptyNodeSub;
	private final int initialCount;
	private int spaceLeft;
	private final NodeCache nodeCache;
	private final NodeSubList nodeSubList;

	public MRUNodes(int size) {
		emptyNodeSub = new NodeSub();
		nodeSubList = new NodeSubList();
		initialCount = size;
		spaceLeft = size;
		nodeCache = new NodeCache();
	}

	public NodeSub insertFromCache(long l) {
		NodeSub nodeSub = (NodeSub) nodeCache.findNodeByID(l);

		if (nodeSub != null) {
			nodeSubList.insertHead(nodeSub);
		}

		return nodeSub;
	}

	public void removeFromCache(NodeSub nodeSub, long l) {
		try {
			if (spaceLeft == 0) {
				NodeSub nodeSub_1 = nodeSubList.popTail();
				nodeSub_1.unlink();
				nodeSub_1.unlinkSub();

				if (nodeSub_1 == emptyNodeSub) {
					NodeSub nodeSub_2 = nodeSubList.popTail();
					nodeSub_2.unlink();
					nodeSub_2.unlinkSub();
				}
			} else {
				spaceLeft--;
			}

			nodeCache.removeFromCache(nodeSub, l);
			nodeSubList.insertHead(nodeSub);
			return;
		} catch (RuntimeException runtimeexception) {
			Signlink.reportError("47547, " + nodeSub + ", " + l + ", " + (byte) 2 + ", " + runtimeexception.toString());
		}

		throw new RuntimeException();
	}

	public void clear() {
		do {
			NodeSub nodeSub = nodeSubList.popTail();

			if (nodeSub != null) {
				nodeSub.unlink();
				nodeSub.unlinkSub();
			} else {
				spaceLeft = initialCount;
				return;
			}
		} while (true);
	}

}
