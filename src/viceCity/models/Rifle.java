package viceCity.models;

public class Rifle extends BaseGun {
    public Rifle(String name) {
        super(name, 50, 500);
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
        return 5;
    }

    private void reload() {
        if (this.getTotalBullets() > 0){
            int remainingTotalBulets = this.getTotalBullets() - 50;

            this.setTotalBullets(remainingTotalBulets);
            this.setBulletsPerBarrel(50);
        }
    }
}
