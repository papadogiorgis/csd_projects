using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class KOUNA3 : MonoBehaviour
{
   public float speed;
   public void Update()
   {
       transform.position = new Vector3(Mathf.Sin(Time.time + 1.57f) * speed*2, 1, Mathf.Sin(Time.time+1.57f) * speed);
   }        
}
