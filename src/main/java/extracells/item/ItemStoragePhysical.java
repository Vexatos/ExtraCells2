package extracells.item;

import appeng.api.config.FuzzyMode;
import appeng.api.implementations.items.IStorageCell;
import appeng.api.storage.data.IAEItemStack;
import extracells.util.inventory.ECCellInventory;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

import java.util.List;

public class ItemStoragePhysical extends Item implements IStorageCell
{
	public ItemStoragePhysical()
	{
		setMaxStackSize(1);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	public static final String[] suffixes =
	{ "256k", "1024k", "4096k", "16384k" };
	public static final int[] bytes_cell =
	{ 262144, 1048576, 4194304, 16777216 };
	public static final int[] types_cell =
	{ 63, 63, 63, 63 };
	private IIcon[] icons;

	public IIcon getIconFromDamage(int dmg)
	{
		return icons[MathHelper.clamp_int(dmg, 0, suffixes.length - 1)];
	}

	@Override
	public void registerIcons(IIconRegister iconRegister)
	{
		icons = new IIcon[suffixes.length];

		for (int i = 0; i < suffixes.length; ++i)
		{
			this.icons[i] = iconRegister.registerIcon("extracells:" + "storage.physical." + suffixes[i]);
		}
	}

	@SuppressWarnings("unchecked")
	public void getSubItems(Item item, CreativeTabs creativeTab, List itemList)
	{
		for (int i = 0; i < suffixes.length; i++)
		{
			itemList.add(new ItemStack(item, 1, i));
		}
	}

	public String getUnlocalizedName(ItemStack itemStack)
	{
		return "extracells.item.storage.physical." + suffixes[itemStack.getItemDamage()];
	}

	@Override
	public int getBytes(ItemStack cellItem)
	{
		return bytes_cell[MathHelper.clamp_int(cellItem.getItemDamage(), 0, suffixes.length - 1)];
	}

	@Override
	public int BytePerType(ItemStack cellItem)
	{
		return bytes_cell[MathHelper.clamp_int(cellItem.getItemDamage(), 0, suffixes.length - 1)] / 128;
	}

	@Override
	public int getTotalTypes(ItemStack cellItem)
	{
		return types_cell[MathHelper.clamp_int(cellItem.getItemDamage(), 0, suffixes.length - 1)];
	}

	@Override
	public boolean isBlackListed(ItemStack cellItem, IAEItemStack requestedAddition)
	{
		return false;
	}

	@Override
	public boolean storableInStorageCell()
	{
		return false;
	}

	@Override
	public boolean isStorageCell(ItemStack i)
	{
		return true;
	}

	@Override
	public double getIdleDrain()
	{
		return 0;
	}

	@Override
	public boolean isEditable(ItemStack is)
	{
		return true;
	}

	@Override
	public IInventory getUpgradesInventory(ItemStack is)
	{
		return new ECCellInventory(is, "upgrades", 2, 1);
	}

	@Override
	public IInventory getConfigInventory(ItemStack is)
	{
		return new ECCellInventory(is, "config", 63, 1);
	}

	@Override
	public FuzzyMode getFuzzyMode(ItemStack is)
	{
		if (!is.hasTagCompound())
			is.setTagCompound(new NBTTagCompound());
		return FuzzyMode.values()[is.getTagCompound().getInteger("fuzzyMode")];
	}

	@Override
	public void setFuzzyMode(ItemStack is, FuzzyMode fzMode)
	{
		if (!is.hasTagCompound())
			is.setTagCompound(new NBTTagCompound());
		is.getTagCompound().setInteger("fuzzyMode", fzMode.ordinal());
	}

	@SuppressWarnings(
	{ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		// TODO
	}
}
