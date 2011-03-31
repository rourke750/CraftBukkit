package net.minecraft.server;

// CraftBukkit start
import org.bukkit.Location;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
// CraftBukkit end

public class ItemBucket extends Item {

    private int a;

    public ItemBucket(int i, int j) {
        super(i);
        this.maxStackSize = 1;
        this.a = j;
    }

    public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
        float f = 1.0F;
        float f1 = entityhuman.lastPitch + (entityhuman.pitch - entityhuman.lastPitch) * f;
        float f2 = entityhuman.lastYaw + (entityhuman.yaw - entityhuman.lastYaw) * f;
        double d0 = entityhuman.lastX + (entityhuman.locX - entityhuman.lastX) * (double) f;
        double d1 = entityhuman.lastY + (entityhuman.locY - entityhuman.lastY) * (double) f + 1.62D - (double) entityhuman.height;
        double d2 = entityhuman.lastZ + (entityhuman.locZ - entityhuman.lastZ) * (double) f;
        Vec3D vec3d = Vec3D.b(d0, d1, d2);
        float f3 = MathHelper.b(-f2 * 0.017453292F - 3.1415927F);
        float f4 = MathHelper.a(-f2 * 0.017453292F - 3.1415927F);
        float f5 = -MathHelper.b(-f1 * 0.017453292F);
        float f6 = MathHelper.a(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = 5.0D;
        Vec3D vec3d1 = vec3d.c((double) f7 * d3, (double) f6 * d3, (double) f8 * d3);
        MovingObjectPosition movingobjectposition = world.a(vec3d, vec3d1, this.a == 0);

        if (movingobjectposition == null) {
            return itemstack;
        } else {
            if (movingobjectposition.a == EnumMovingObjectType.TILE) {
                int i = movingobjectposition.b;
                int j = movingobjectposition.c;
                int k = movingobjectposition.d;

                if (!world.a(entityhuman, i, j, k)) {
                    return itemstack;
                }

                if (this.a == 0) {
                    if (world.getMaterial(i, j, k) == Material.WATER && world.getData(i, j, k) == 0) {
                        // CraftBukkit start
                        PlayerBucketFillEvent event = CraftEventFactory.callPlayerBucketFillEvent(entityhuman, i, j, k, -1, itemstack, Item.WATER_BUCKET);

                        if (event.isCancelled()) {
                            return itemstack;
                        }

                        CraftItemStack itemInHand = (CraftItemStack) event.getItemStack();
                        byte data = itemInHand.getData() == null ? (byte) 0 : itemInHand.getData().getData();
                        // CraftBukkit end

                        world.e(i, j, k, 0);
                        return new ItemStack(itemInHand.getTypeId(), itemInHand.getAmount(), data); // CraftBukkit
                    }

                    if (world.getMaterial(i, j, k) == Material.LAVA && world.getData(i, j, k) == 0) {
                        // CraftBukkit start
                        PlayerBucketFillEvent event = CraftEventFactory.callPlayerBucketFillEvent(entityhuman, i, j, k, -1, itemstack, Item.LAVA_BUCKET);

                        if (event.isCancelled()) {
                            return itemstack;
                        }

                        CraftItemStack itemInHand = (CraftItemStack) event.getItemStack();
                        byte data = itemInHand.getData() == null ? (byte) 0 : itemInHand.getData().getData();
                        // CraftBukkit end

                        world.e(i, j, k, 0);
                        return new ItemStack(itemInHand.getTypeId(), itemInHand.getAmount(), data ); // CraftBukkit
                    }
                } else {
                    if (this.a < 0) {
                        // CraftBukkit start
                        PlayerBucketEmptyEvent event = CraftEventFactory.callPlayerBucketEmptyEvent(entityhuman, i, j, k, movingobjectposition.e, itemstack);

                        if (event.isCancelled()) {
                            return itemstack;
                        }

                        CraftItemStack itemInHand = (CraftItemStack) event.getItemStack();
                        byte data = itemInHand.getData() == null ? (byte) 0 : itemInHand.getData().getData();
                        return new ItemStack(itemInHand.getTypeId(), itemInHand.getAmount(), data );
                        // CraftBukkit end
                    }
                    int clickedX = i, clickedY = j, clickedZ = k; // CraftBukkit

                    if (movingobjectposition.e == 0) {
                        --j;
                    }

                    if (movingobjectposition.e == 1) {
                        ++j;
                    }

                    if (movingobjectposition.e == 2) {
                        --k;
                    }

                    if (movingobjectposition.e == 3) {
                        ++k;
                    }

                    if (movingobjectposition.e == 4) {
                        --i;
                    }

                    if (movingobjectposition.e == 5) {
                        ++i;
                    }

                    if (world.isEmpty(i, j, k) || !world.getMaterial(i, j, k).isBuildable()) {
                        // CraftBukkit start
                        PlayerBucketEmptyEvent event = CraftEventFactory.callPlayerBucketEmptyEvent(entityhuman, clickedX, clickedY, clickedZ, movingobjectposition.e, itemstack);

                        if (event.isCancelled()) {
                            return itemstack;
                        }
                        // CraftBukkit end

                        if (world.m.d && this.a == Block.WATER.id) {
                            world.a(d0 + 0.5D, d1 + 0.5D, d2 + 0.5D, "random.fizz", 0.5F, 2.6F + (world.k.nextFloat() - world.k.nextFloat()) * 0.8F);

                            for (int l = 0; l < 8; ++l) {
                                world.a("largesmoke", (double) i + Math.random(), (double) j + Math.random(), (double) k + Math.random(), 0.0D, 0.0D, 0.0D);
                            }
                        } else {
                            world.b(i, j, k, this.a, 0);
                        }

                        // CraftBukkit start
                        CraftItemStack itemInHand = (CraftItemStack) event.getItemStack();
                        byte data = itemInHand.getData() == null ? (byte) 0 : itemInHand.getData().getData();
                        return new ItemStack(itemInHand.getTypeId(), itemInHand.getAmount(), data );
                        // CraftBukkit end
                    }
                }
            } else if (this.a == 0 && movingobjectposition.g instanceof EntityCow) {
                // CraftBukkit start -- This codepath seems to be *NEVER* called 
                Location loc = movingobjectposition.g.getBukkitEntity().getLocation();
                PlayerBucketFillEvent event = CraftEventFactory.callPlayerBucketFillEvent(entityhuman, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), -1, itemstack, Item.MILK_BUCKET);

                if (event.isCancelled()) {
                    return itemstack;
                }

                CraftItemStack itemInHand = (CraftItemStack) event.getItemStack();
                byte data = itemInHand.getData() == null ? (byte) 0 : itemInHand.getData().getData();
                return new ItemStack(itemInHand.getTypeId(), itemInHand.getAmount(), data );
                // CraftBukkit end
            }

            return itemstack;
        }
    }
}
