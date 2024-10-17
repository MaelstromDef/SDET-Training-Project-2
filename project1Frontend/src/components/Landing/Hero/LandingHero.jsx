import HeroButtons from "./HeroButtons";
import HeroText from "./HeroText";
import styles from "./LandingHero.module.css";

import background from '/src/assets/WarehouseShelvesCropped.jpg';

const LandingHero = () => {
  return (
    <article className={styles.article}>
      <picture className={styles.picture}>
        <source media="(min-width: 0px)" srcSet={background} />
        <img className={styles.image} src={background} alt="background" />      
      </picture>
      <div className={styles.overlay}>
        <HeroText />
        <HeroButtons />
      </div>
    </article>
  );
};

export default LandingHero;