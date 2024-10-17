import styles from "./LandingHero.module.css";

export default function HeroText (){
    return <div className={styles.textbox}>
        <h1 className={styles.header}>Manage your warehouses today.</h1>
        <p className={styles.text}>Inventory Control.</p>
        <p className={styles.text}>Employee Management.</p>
        <p className={styles.text}>And so much more.</p>
    </div>
}