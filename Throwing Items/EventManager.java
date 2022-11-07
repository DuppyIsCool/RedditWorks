package me.DuppyIsCool.MyPlugin;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class EventManager implements Listener{
	
	//This fires when a player interacts 
	@EventHandler
	public void onTarget(PlayerInteractEvent event) {
		
		//If the player right clicks the air or a block
		if(event.getAction() == Action.RIGHT_CLICK_AIR | event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			//Get the player
			Player p = event.getPlayer();
			
			//Get the item in the player's main hand
			ItemStack heldItem = p.getInventory().getItemInMainHand();
			
			//If the item is not null and its type is FLINT
			if(!heldItem.equals(null)) {
				if(heldItem.getType().equals(Material.FLINT)) {
					//Call the function to throw the item
					throwItem(p);
					heldItem.setAmount(heldItem.getAmount()-1);
					
					//I want to play a little 'plop' sound effect when you throw. You can change this to any sound
					p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EGG_THROW, 1, 1);
				}
			}
		}
	}
	
	@EventHandler
	public void onHitEvent(ProjectileHitEvent event) {
		if(event.getEntity().isValid()) {
			if(event.getEntity().getType() == EntityType.ARROW) {
				//If its an arrow, cast to the proper data type
				Arrow arrow = (Arrow) event.getEntity();
				
				//check if its our special arrow
				if(arrow.getCustomName().equals("parent arrow")) {
					//Eject the item off of the arrow
					arrow.eject();
					//Delete the arrow entity
					arrow.remove();
				}
			}
		}
	}
	
	//This method is responsible for throwing the player's main hand item
	private void throwItem(Player p) {	
		//Setup our ItemStacks
		ItemStack heldItem = p.getInventory().getItemInMainHand();
		ItemStack throwItem = new ItemStack(heldItem);
		
		//We throw one item at a time, so set the item to 1
		throwItem.setAmount(1);

		//Get where our player is looking
		Location playerLocation = p.getEyeLocation();
		
		//Spawn our Entity of type Item in the world at the player's eye location with the properties of our throwItem ItemStack
		Item thrownItemEntity = p.getWorld().dropItem(playerLocation, throwItem);
				
		//Make it so the item cannot be picked up for a  bit 
		thrownItemEntity.setPickupDelay(20);
		
		//Create the projectile that our item entity rides on
		Arrow parentArrow = p.getWorld().spawnArrow(playerLocation, playerLocation.getDirection(), 1.2f, 0);
		
		//Set our Item Entity to ride the arrow
		parentArrow.addPassenger(thrownItemEntity);
		
		//Set The Attributes
		parentArrow.setDamage(1);
		parentArrow.setCustomName("parent arrow");
		parentArrow.setSilent(true);
		parentArrow.setVisualFire(false);
		
	}	
}
