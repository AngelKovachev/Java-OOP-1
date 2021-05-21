package viceCity.models;

public class Pistol extends BaseGun {
    public Pistol(String name) {
        super(name, 10, 100);
    }

    @Override
    public int fire() {
        if (this.getBulletsPerBarrel() == 0){
            this.reload();
            if (this.getBulletsPerBarrel() == 0){
                return 0;
            }
        }
        this.setBulletsPerBarrel(this.getBulletsPerBarrel() - 1);
        return 1;
    }

    private void reload() {
       if (this.getTotalBullets() > 0){
           int remainingTotalBulets = this.getTotalBullets() - 10;

           this.setTotalBullets(remainingTotalBulets);
           this.setBulletsPerBarrel(10);
       }
    }
}
