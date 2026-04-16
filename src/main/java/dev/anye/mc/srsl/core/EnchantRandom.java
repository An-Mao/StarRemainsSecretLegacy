package dev.anye.mc.srsl.core;

import java.util.List;
import java.util.Random;

public class EnchantRandom {
    private final List<Enchant> enchantList;
    private final int[] cumulativeWeights;
    private final Random random;
    private final int totalWeight;

    public EnchantRandom(List<Enchant> enchantList) {
        this.enchantList = enchantList;
        this.cumulativeWeights = new int[enchantList.size()];
        this.random = new Random();

        int cumulativeWeight = 0;
        for (int i = 0; i < enchantList.size(); i++) {
            cumulativeWeight += enchantList.get(i).getWeight();
            cumulativeWeights[i] = cumulativeWeight;
        }
        this.totalWeight = cumulativeWeight;
    }

    public Enchant getRandomItem() {
        int randomNumber = random.nextInt(totalWeight);

        int index = binarySearch(randomNumber);
        return enchantList.get(index);
    }

    private int binarySearch(int value) {
        int low = 0;
        int high = cumulativeWeights.length - 1;

        while (low < high) {
            int mid = (low + high) / 2;
            if (value < cumulativeWeights[mid]) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    public int getTotalWeight() {
        return totalWeight;
    }
}
