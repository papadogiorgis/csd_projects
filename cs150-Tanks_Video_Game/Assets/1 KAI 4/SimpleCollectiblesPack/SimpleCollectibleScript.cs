using System.Collections;
using System.Collections.Generic;
using UnityEngine;

[RequireComponent(typeof(AudioSource))]
public class SimpleCollectibleScript : MonoBehaviour {

	public enum CollectibleTypes {HealthPack , Trap, kathister};

	public CollectibleTypes CollectibleType; // this gameObject's type

	public bool rotate; // do you want it to rotate?

	public float rotationSpeed;

	public AudioClip collectSound;

	public GameObject collectEffect;


	//dimiourgia
	void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {

		if (rotate)
			transform.Rotate (Vector3.up * rotationSpeed * Time.deltaTime , Space.World);

	}

	void OnTriggerEnter(Collider other)
	{
		if (other.tag == "Player") {
			Collect (other);
		}
	}

	public void Collect(Collider other)
	{
		if(collectSound)
			AudioSource.PlayClipAtPoint(collectSound , transform.position);
		if(collectEffect)
			Instantiate(collectEffect, transform.position , Quaternion.identity);

        if (CollectibleType == CollectibleTypes.HealthPack)//an einai healthpack
        {
			other.gameObject.GetComponent<TankHealth>().TakeHealth(40f);//vale 40 health
        }
		if (CollectibleType == CollectibleTypes.Trap)//an einai pagida
		{
			other.gameObject.GetComponent<TankHealth>().TakeDamage(60f);//afairese 60 health
		}
		if (CollectibleType == CollectibleTypes.kathister)//an einai ATHWO ANTIKEIMENO (4)
		{
			other.gameObject.GetComponent<TankMovement>().kathisterisi();
		}

		Destroy (gameObject);
	}
}